/*
 * Copyright (C) 2013 ferdi
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 */

package com.guang.eunormia.group;

import com.guang.eunormia.atomic.EunormiaSingle;
import com.guang.eunormia.common.Node;
import com.guang.eunormia.common.binary.RedisCommands;
import com.guang.eunormia.common.config.HAConfig.ServerProperties;
import com.guang.eunormia.common.config.Router;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *　master/slave 策略路由
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 3:40:18 PM
 * @version 0.0.1
*/
public class MSRandomRouter implements Router
{
    static final Log logger = LogFactory.getLog(MSRandomRouter.class);
    Random random = new Random();
    private List<ServerProperties> all_props;// 保存最开始加载的配置,里面可能有失效的服务器。
    boolean failover;
    /**
     * 可能在出现异常的时候被修改掉。 然后会使用copy on write 的方式，3s
     * check一次，如果节点回复，就会将它加入列表，也就是会被修改回来。
     */
    volatile RouteData routeData;

    private RouteData allRouteData;// 保存最开始加载的配置,里面可能有失效的服务器。

    private RouteData masterRouteData;
    /**
     * single
     */
    final Map<String, EunormiaSingle> singleCache = new HashMap<String, EunormiaSingle>();
    ExecutorService executor_retry = Executors.newSingleThreadExecutor();
    final Retry retry = new Retry();

    public MSRandomRouter(List<ServerProperties> props, boolean failover) {
        this.all_props = props;
        this.failover = failover;
        routeData = createRandomData(props);
        allRouteData = createRandomData(props);
        masterRouteData = createMasterData(props);
        startRetry();
    }

    private RouteData createRandomData(List<ServerProperties> props) {
        int[] weights = new int[props.size()];
        EunormiaSingle[] group = new EunormiaSingle[props.size()];
        int prev = 0;
        for (int i = 0; i < props.size(); i++) {
            group[i] = getAtomic(props.get(i));
            weights[i] += prev + props.get(i).readWeight;
            prev = weights[i];
        }

        return new RouteData(props, weights, group);
    }

    private RouteData createMasterData(List<ServerProperties> props) {
        int[] weights = new int[1];
        EunormiaSingle[] group = new EunormiaSingle[1];
        group[0] = getAtomic(props.get(0));
        weights[0] += props.get(0).readWeight;
        logger.info("master enabled, master is " + group[0]);
        return new RouteData(props, weights, group);
    }

    // 如果外部出现变更，就把变更带入进来，变更锁，防止重复创建。
    // 出错，从已有路由列表中去除该single.
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public synchronized void onError(Node single) {
        if (!failover) {
            return;
        }
        logger.warn("onError:" + single);

        ServerProperties prop = single.getProperties();
        List<ServerProperties> new_props = (List<ServerProperties>) ((ArrayList) routeData.props).clone();
        new_props.remove(prop);
        routeData = createRandomData(new_props);
        // Master 不能改变
        masterRouteData = createMasterData(this.all_props);

        // 触发重试逻辑
        retry.addRetry(single);
    }

    /**
     * 失败的连接，又重新连上了。 需要在当前使用的列表添加一个。
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private synchronized void onReturn(EunormiaSingle single) {

        logger.warn("onReturn:" + single);

        ServerProperties prop = single.getProperties();
        List<ServerProperties> new_props = (List<ServerProperties>) ((ArrayList) routeData.props).clone();
        if (!new_props.contains(single.getProperties())) {
            new_props.add(prop);
        }
        routeData = createRandomData(new_props);
    }

    private Node route(RouteData routeData) throws Exception {
        int max_random = routeData.weights[routeData.weights.length - 1];
        int x = random.nextInt(max_random);
        for (int i = 0; i < routeData.weights.length; i++) {
            if (x < routeData.weights[i]) {
                return routeData.group[i];
            }
        }
        throw new Exception("routeData is empty");
    }

    @Override
    public Node route() throws Exception {
        return route(routeData);
    }

    @Override
    public RouteData getReadData() {
        return routeData;
    }

    @Override
    public RouteData getWriteData() {
        return masterRouteData;
    }

    @Override
    public RouteData getAllRouteData() {
        return allRouteData;
    }

    synchronized EunormiaSingle getAtomic(ServerProperties prop) {
        EunormiaSingle s = singleCache.get(prop.generateKey());
        if (s == null) {
            s = new EunormiaSingle(prop);
            singleCache.put(prop.generateKey(), s);
        }
        return s;
    }

    @Override
    public Node getAtomic(String key) {
        return singleCache.get(key);
    }

    @Override
    public void destroy() {
        retry.exit = true;
        synchronized (retry) {
            retry.notify();
        }
        executor_retry.shutdownNow();
        synchronized (singleCache) {
            for (Map.Entry<String, EunormiaSingle> entry : singleCache.entrySet()) {
                entry.getValue().destroy();
            }
        }
    }

    final void startRetry() {
        executor_retry.execute(retry);
    }

    final class Retry implements Runnable {

        volatile boolean exit = false;
        CopyOnWriteArraySet<Node> set = new CopyOnWriteArraySet<Node>();

        public void addRetry(Node single) {
            set.add(single);
            synchronized (this) {
                this.notify();
            }
        }

        @Override
        public void run() {

            while (!exit) {
                for (Node s : set) {
                    logger.warn("retry:" + s);
                    try {
                        synchronized (singleCache) {
                            singleCache.remove(s.getProperties().generateKey()).destroy();
                        }
                    } catch (Exception e) {
                        logger.warn("", e);
                    }
                    EunormiaSingle ss = null;
                    try {
                        ss = new EunormiaSingle(s.getProperties());
                        RedisCommands eunormia = ss.getEunormia();
                        eunormia.ping();
                        synchronized (singleCache) {
                            singleCache.put(ss.getProperties().generateKey(), ss);
                        }
                        // success
                        onReturn(ss);
                        set.remove(s);
                    } catch (Throwable t) {
                        s.getErrorCount().incrementAndGet();
                        if (ss != null) {
                            try {
                                ss.destroy();
                            } catch (Exception e) {
                            }
                        }
                        logger.warn("retry throwable : " + s, t);
                    }

                }
                try {
                    synchronized (Retry.this) {
                        wait(1000 * 20);
                    }
                } catch (InterruptedException ex) {
                    logger.warn("Retry Thread InterruptedException", ex);
                }
            }

        }
    }

    @Override
    public String toString() {
        return "RandomRouter{" + "all_props=" + all_props + ", routeData=" + routeData + '}';
    }

}
