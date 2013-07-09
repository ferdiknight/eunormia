/*
 * Copyright (C) 2013 ferdi
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 */
package com.guang.eunormia.group.commands;

import com.guang.eunormia.common.core.EunormiaManager;
import com.guang.eunormia.common.serializer.EunormiaSerializer;
import com.guang.eunormia.group.EunormiaCluster;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Eunormia Node 管理器工厂
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 3:53:12 PM
 * @version 0.0.1
 */
public class EunormiaManagerFactory
{

    private static ConcurrentHashMap<String, EunormiaManager> managers = new ConcurrentHashMap<String, EunormiaManager>();

    /**
     * 创建全局唯一的实例，如果已经存在，则返回存在的实例
     *
     * @param appName
     * @param version
     * @return
     */
    public static synchronized EunormiaManager create(String appName, String version,String zkAddress)
    {
        String key = appName + "-" + version;
        EunormiaManager manager = managers.get(key);
        if (manager == null)
        {
            EunormiaCluster eunormiaCluster = new EunormiaCluster(appName, version,zkAddress);
            eunormiaCluster.init();
            
            manager = new DefaultEunormiaManager(eunormiaCluster);
            managers.put(key, manager);
        }
        return manager;
    }

    /**
     * 创建全局唯一的实例，如果已经存在，则返回存在的实例
     *
     * @param appName
     * @param version
     * @param serializer
     * @return
     */
    public static synchronized EunormiaManager create(String appName, String version,String zkAddress, EunormiaSerializer<?> serializer)
    {
        String key = appName + "-" + version;
        DefaultEunormiaManager manager = (DefaultEunormiaManager) managers.get(key);
        if (manager == null)
        {
            EunormiaCluster eunormiaCluster = new EunormiaCluster(appName, version,zkAddress);
            eunormiaCluster.init();
            manager = new DefaultEunormiaManager(eunormiaCluster);
            managers.put(key, manager);
        }
        manager.setKeySerializer(serializer);
        manager.setValueSerializer(serializer);
        manager.setHashKeySerializer(serializer);
        return manager;
    }
    
    public static synchronized EunormiaManager create(String appName, String version,String zkAddress,EunormiaSerializer<?> keySerializer,EunormiaSerializer<?> hashKeySerializer,EunormiaSerializer<?> valueSerializer)
    {
        String key = appName + "-" + version;
        DefaultEunormiaManager manager = (DefaultEunormiaManager) managers.get(key);
        if (manager == null)
        {
            EunormiaCluster eunormiaCluster = new EunormiaCluster(appName, version,zkAddress);
            eunormiaCluster.init();
            manager = new DefaultEunormiaManager(eunormiaCluster);
            managers.put(key, manager);
        }
        manager.setKeySerializer(keySerializer);
        manager.setValueSerializer(hashKeySerializer);
        manager.setHashKeySerializer(valueSerializer);
        return manager;
    }

    /**
     * 销毁指定的EunormiaManager。注意：在多线程环境下需要考虑是否还有其他线程还在使用，否则会导致问题。
     *
     * @param manager
     */
    public static synchronized void destroy(EunormiaManager manager)
    {
        Iterator<Map.Entry<String, EunormiaManager>> it = managers.entrySet().iterator();
        while (it.hasNext())
        {
            if (it.next().getValue().equals(manager))
            {
                it.remove();
            }
        }
        manager.destroy();
    }
}
