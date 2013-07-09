/**
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation.
 *
 */
package com.guang.eunormia.common.core;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * key operate
 * @author ferdi email:ferdi@blueferdi.com
 * @since 2013-06-28 15:00:00
 * @version 0.0.1
 */
public interface ValueCommands<K>
{
    /**
     * 设置数据，如果数据已经存在，则覆盖，如果不存在，则新增
     * 
     * @param namespace
     *            数据所在的namespace
     * @param key
     * @param value
     */
     <V> void set(int namespace, K key, V value);

    /**
     * 设置数据，如果数据已经存在，则覆盖，如果不存在，则新增
     * 
     * @param namespace
     *            数据所在的namespace
     * @param key
     * @param value
     * @param timeout
     *            数据的有效时间
     * @param unit
     *            时间单位
     */
     <V> void set(int namespace, K key, V value, long timeout, TimeUnit unit);

    /**
     * 设置数据，如果数据已经存在，则保留原值返回<tt>false</tt>，如果不存在，则新增，返回<tt>true</tt>
     * 
     * @param namespace
     *            数据所在的namespace
     * @param key
     * @param value
     * @return 如果数据不存在返回<tt>true</tt>，否则返回<tt>false</tt>
     */
     <V> Boolean setIfAbsent(int namespace, K key, V value);

    /**
     * 批量设置数据
     * 
     * @param namespace
     * @param m
     */
     <V> void multiSet(int namespace, Map<? extends K, ? extends V> m);

    /**
     * 批量设置数据，只有当数据不存在时才设置
     * 
     * @param namespace
     * @param m
     */
     <V> void multiSetIfAbsent(int namespace, Map<? extends K, ? extends V> m);

    /**
     * 获取数据 注意不能用此方法去获取increment方法产生的key
     * 
     * @param namespace
     * @param key
     * @return
     */
     <V> V get(int namespace, K key);

    /**
     * 使用LocalCache的方式获取数据，降低频繁交互造成的性能损失<br/>
     * 首先到LocalCache中去取数据，如果取不到，则到Redis中取，并且把取得的值按照超时时间放入LocalCache
     * 
     * @since V1.0.1
     * @param namespace
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
     <V> V get(int namespace, K key, long timeout, TimeUnit unit);

    /**
     * 使用LocalCache的方式获取数据，降低频繁交互造成的性能损失<br/>
     * 首先到LocalCache中去取数据，如果取不到，则到Redis中取，并且把取得的值按照超时时间放入LocalCache
     * 
     * @param <K>
     * @param <V>
     * @param namespace
     * @param key
     * @param expireAt
     * @return
     */
     <V> V get(int namespace, K key, Date expireAt);

    /**
     * 设置数据同时返回老数据，此方法是原子操作 注意不能用此方法去获取increment方法产生的key
     * 
     * @param namespace
     * @param key
     * @param value
     * @return
     */
     <V> V getAndSet(int namespace, K key, V value);

    /**
     * 批量获取数据 注意不能用此方法去获取increment方法产生的key
     * 
     * @param namespace
     * @param keys
     * @return
     */
     <V> List<V> multiGet(int namespace, Collection<K> keys);

    /**
     * 使用LocalCache的方式获取数据，降低频繁交互造成的性能损失<br/>
     * 首先到LocalCache中去取数据，如果取不到，则到Redis中取，并且把取得的值按照超时时间放入LocalCache
     * 
     * @since V1.0.1
     * @param namespace
     * @param keys
     * @param timeout
     * @param unit
     * @return
     */
     <V> List<V> multiGet(int namespace, Collection<K> keys, long timeout, TimeUnit unit);

    /**
     * 使用LocalCache的方式获取数据，降低频繁交互造成的性能损失<br/>
     * 首先到LocalCache中去取数据，如果取不到，则到Redis中取，并且把取得的值按照超时时间放入LocalCache
     * 
     * @since V1.0.1
     * @param <K>
     * @param <V>
     * @param namespace
     * @param keys
     * @param expireAt
     * @return
     */
     <V> List<V> multiGet(int namespace, Collection<K> keys, Date expireAt);
}
