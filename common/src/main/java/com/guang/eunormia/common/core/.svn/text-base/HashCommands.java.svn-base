/**
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation.
 *
 */
package com.guang.eunormia.common.core;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * hashes operate
 * @author ferdi email:ferdi@blueferdi.com
 * @since 2013-06-28 14:27:00
 * @version 0.0.1
 */
public interface HashCommands<K>
{
    /**
     * 删除<tt>key</tt>的Hash对象的<tt>hashKey</tt>的数据
     *
     * @param namespace
     * @param key
     * @param hashKey
     */
   <HK> void delete(int namespace, K key, HK... hashKey);

    /**
     * 判断<tt>key</tt>的Hash对象的<tt>hashKey</tt>是否存在
     *
     * @param namespace
     * @param key
     * @param hashKey
     * @return 存在返回<tt>true</tt>，不存在返回<tt>false</tt>
     */
    <HK> Boolean hasKey(int namespace, K key, HK hashKey);

    /**
     * 取得<tt>key</tt>的Hash对象的<tt>hashKey</tt>的数据
     *
     * @param namespace
     * @param key
     * @param hashKey
     * @return
     */
    <HK,V> V get(int namespace, K key, HK hashKey);

    /**
     * 批量取得数据
     *
     * @param namespace
     * @param key
     * @param hashKeys
     * @return
     */
    <HK,V> Collection<V> multiGet(int namespace, K key, Collection<HK> hashKeys);

    /**
     * 计数操作方法 <tt>delta</tt>可为正、负、0值。正值为增加计数、负值为减少计数、0为取得当前值
     *
     * @param namespace
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    <HK> Long increment(int namespace, K key, HK hashKey, long delta);

    /**
     * 得到<tt>key</tt>的Hash对象的所有key
     *
     * @param namespace
     * @param key
     * @return
     */
    <HK> Set<HK> keys(int namespace, K key);

    /**
     * 缺的<tt>key</tt>的Hash对象的大小
     *
     * @param namespace
     * @param key
     * @return
     */
    Long size(int namespace, K key);

    /**
     * 批量设置数据
     *
     * @param namespace
     * @param key
     * @param m
     */
    <HK,V> void putAll(int namespace, K key, Map<? extends HK, ? extends V> m);

    /**
     * 设置数据，如果数据存在，则覆盖，如果数据不存在，则新增
     *
     * @param namespace
     * @param key
     * @param hashKey
     * @param value
     */
    <HK,V> void put(int namespace, K key, HK hashKey, V value);

    /**
     * 设置数据，只有数据不存在才能设置成功
     *
     * @param namespace
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    <HK,V> Boolean putIfAbsent(int namespace, K key, HK hashKey, V value);

    /**
     * 取得<tt>key</tt>的Hash对象的所有值
     *
     * @param namespace
     * @param key
     * @return
     */
    <V> Collection<V> values(int namespace, K key);

    /**
     * 缺的<tt>key</tt>的Hash对象的所有key、value
     *
     * @param namespace
     * @param key
     * @return
     */
    <HK,V> Map<HK, V> entries(int namespace, K key);
}
