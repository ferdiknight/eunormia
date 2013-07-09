/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation.
 *
 */
package com.guang.eunormia.common.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * atomic count operate
 * @author ferdi email:ferdi@blueferdi.com
 * @since 2013-06-28 14:54:00
 * @version 0.0.1
 */
public interface AtomicCommands<K>
{
    /**
     * 计数操作方法 <tt>delta</tt>可为正、负、0值。正值为增加计数、负值为减少计数、0为取得当前值
     * 
     * @param namespace
     * @param key
     * @param delta
     * @return
     */
    Long increment(int namespace, K key, long delta);

    /**
     * 设置数据，如果数据已经存在，则覆盖，如果不存在，则新增
     * 
     * @param namespace
     *            数据所在的namespace
     * @param key
     * @param value
     */
    void set(int namespace, K key, long value);

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
    void set(int namespace, K key, long value, long timeout, TimeUnit unit);

    /**
     * 设置数据，如果数据已经存在，则保留原值返回<tt>false</tt>，如果不存在，则新增，返回<tt>true</tt>
     * 
     * @param namespace
     *            数据所在的namespace
     * @param key
     * @param value
     * @return 如果数据不存在返回<tt>true</tt>，否则返回<tt>false</tt>
     */
    Boolean setIfAbsent(int namespace, K key, long value);

    /**
     * 批量设置数据
     * 
     * @param namespace
     * @param m
     */
    void multiSet(int namespace, Map<? extends K, Long> m);

    /**
     * 批量设置数据，只有当数据不存在时才设置
     * 
     * @param namespace
     * @param m
     */
    void multiSetIfAbsent(int namespace, Map<? extends K, Long> m);

    /**
     * 获取数据
     * 
     * @param namespace
     * @param key
     * @return
     */
    long get(int namespace, K key);

    /**
     * 设置数据同时返回老数据，此方法是原子操作
     * 
     * @param namespace
     * @param key
     * @param value
     * @return
     */
    long getAndSet(int namespace, K key, long value);

    /**
     * 批量获取数据
     * 
     * @param namespace
     * @param keys
     * @return
     */
    List<Long> multiGet(int namespace, Collection<? extends K> keys);
}
