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

import com.guang.eunormia.common.Cluster;
import com.guang.eunormia.common.EunormiaConnectionException;
import com.guang.eunormia.common.EunormiaException;
import com.guang.eunormia.common.Node;
import com.guang.eunormia.common.binary.RedisCommands;
import com.guang.eunormia.common.config.ConfigManager;
import com.guang.eunormia.common.config.Process;
import com.guang.eunormia.common.config.Process.Policy;
import com.guang.eunormia.common.config.Router;
import com.guang.eunormia.common.monitor.BufferedStatLogWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * EunormiaNode的集群封装，屏蔽具体逻辑
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 4:21:34 PM
 * @version 0.0.1
 */
public class EunormiaCluster implements Cluster
{

    static final Log logger = LogFactory.getLog(EunormiaCluster.class);
    private ConfigManager cm;
    private RedisCommands node;
    private String appName;
    private String version;
    private String zkAddress;
    private volatile boolean inited = false;
    private final static int retryTimes = 3;

    public EunormiaCluster()
    {
    }

    public EunormiaCluster(String appName, String version, String zkAddress)
    {
        this.appName = appName;
        this.version = version;
        this.zkAddress = zkAddress;
    }

    @Override
    public void init()
    {
        if (!inited)
        {
            try
            {
                if (this.cm == null)
                {
                    ZookeeperConfigManager zcm = new ZookeeperConfigManager(appName, version);
                    zcm.setZkAddress(zkAddress);
                    zcm.init();
                    this.cm = zcm;
                }

                node = (RedisCommands) Proxy.newProxyInstance(RedisCommands.class.getClassLoader(), new Class[]
                {
                    RedisCommands.class
                }, new RedisGroupInvocationHandler());
            }
            catch (Exception e)
            {
                throw new EunormiaException("init failed", e);
            }
            inited = true;
        }
    }

    @Override
    public RedisCommands getEunormia()
    {
        if (node == null)
        {
            throw new EunormiaException("please invoke the init method first.");
        }
        return node;
    }

    public class RedisGroupInvocationHandler implements InvocationHandler
    {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
        {
            long time = System.currentTimeMillis();
            String name = method.getName();
            Router rr = cm.getRouter();
            Process annotation = method.getAnnotation(Process.class);
            Throwable exception = null;
            if (annotation.value() == Policy.READ)
            {
                int errorCount = 0;
                while (rr.getReadData().props.size() > 0)
                {
                    Node s = rr.route();
                    try
                    {
                        Object result = method.invoke(s.getEunormia(), args);
                        statLog(name, true, time);
                        return result;
                    }
                    catch (Throwable t)
                    {
                        statLog(name, false, time);
                        errorCount++;
                        if (errorCount < retryTimes)
                        {
                            continue;
                        }
                        exception = t;
                        logger.warn("read exception:" + s.getProperties(), t);
                        boolean connectionError = false;
                        try
                        {
                            if (t instanceof InvocationTargetException)
                            {// 解包异常
                                InvocationTargetException ite = (InvocationTargetException) t;
                                UndeclaredThrowableException ute = (UndeclaredThrowableException) ite.getTargetException();
                                if (ute.getUndeclaredThrowable() instanceof TimeoutException)
                                {
                                    connectionError = true;
                                    rr.onError(s);
                                }
                                else
                                {
                                    ExecutionException ee = (ExecutionException) ute.getUndeclaredThrowable();
                                    InvocationTargetException ite_1 = (InvocationTargetException) ee.getCause();
                                    if (ite_1.getCause() instanceof ClassCastException)
                                    {
                                        connectionError = true;
                                        rr.onError(s);
                                    }
                                    else
                                    {
                                        EunormiaException te = (EunormiaException) ite_1.getTargetException();
                                        if (te instanceof EunormiaConnectionException || te.getCause() instanceof EunormiaConnectionException)
                                        {
                                            connectionError = true;
                                            rr.onError(s);
                                        }
                                    }
                                }
                            }
                        }
                        catch (Throwable tt)
                        {
                            logger.warn("解包异常:", tt);
                            // 可能会抛出转换异常,符合预期,如果碰到转换异常,直接在connection error过程中从新抛出
                        }

                        if (!connectionError)
                        {
                            throw t;
                        }
                    }
                }

                throw new Exception("Read RouteData is empty," + rr, exception);
            }
            else if (annotation.value() == Policy.WRITE)
            {
                Node[] ss = rr.getWriteData().group;
                if (ss == null || ss.length == 0)
                {
                    throw new Exception("write RouteData is empty," + rr, exception);
                }
                Object result = null;
                int e = 0;
                for (Node s : ss)
                {
                    for (int i = 0; i < retryTimes; i++)
                    {
                        try
                        {
                            result = method.invoke(s.getEunormia(), args);
                        }
                        catch (Throwable t)
                        {
                            if (i == retryTimes - 1)
                            {
                                e++;
                                statLog(name, false, time);
                                logger.warn("write exception:" + s.getProperties(), t);
                                exception = t;
                                try
                                {
                                    // 解包异常
                                    InvocationTargetException ite = (InvocationTargetException) t;
                                    UndeclaredThrowableException ute = (UndeclaredThrowableException) ite.getTargetException();
                                    if (ute.getUndeclaredThrowable() instanceof TimeoutException)
                                    {
                                        rr.onError(s);
                                    }
                                    else
                                    {
                                        ExecutionException ee = (ExecutionException) ute.getUndeclaredThrowable();
                                        InvocationTargetException ite_1 = (InvocationTargetException) ee.getCause();
                                        if (ite_1.getCause() instanceof ClassCastException)
                                        {
                                            rr.onError(s);
                                        }
                                        else
                                        {
                                            EunormiaException te = (EunormiaException) ite_1.getTargetException();
                                            if (te instanceof EunormiaConnectionException || te.getCause() instanceof EunormiaConnectionException)
                                            {
                                                rr.onError(s);
                                            }
                                        }
                                    }
                                }
                                catch (Throwable tt)
                                {
                                    logger.warn("解包异常:", tt);
                                }
                            }
                            else
                            {
                                continue;
                            }
                        }
                        break;
                    }
                }

                if (e >= 2)
                {// 全部都抛异常了,告知调用端
                    throw exception;
                }
                statLog(name, true, time);
                return result;
            }
            else if ("toString".equals(name))
            {
                return "";
            }
            else if ("hashCode".equals(name))
            {
                Node s = rr.route();
                if (s != null)
                {
                    return s.hashCode();
                }
                else
                {
                    return 0;
                }
            }
            else if ("equals".equals(name))
            {
                Node s = rr.route();
                if (args.length == 1)
                {
                    return s.equals(args[0]);
                }
            }
            statLog(name, false, time);
            throw new Exception("method don't match:" + name);
        }
    }

    private void statLog(String methodName, Boolean flag, long time)
    {
        BufferedStatLogWriter.add(appName, methodName, flag, 1, System.currentTimeMillis() - time);
    }

    @Override
    public void destroy()
    {
        Router rr = cm.getRouter();
        rr.destroy();
        // ConfigMananger.destroy();
    }

    public String getAppName()
    {
        return appName;
    }

    public void setAppName(String appName)
    {
        this.appName = appName;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    @Override
    public void setConfigManager(ConfigManager cm)
    {
        this.cm = cm;
    }

    public ConfigManager getConfigManager()
    {
        return cm;
    }
}
