/**
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation.
 *
 */
package com.guang.eunormia.common.core;

import java.util.Collection;
import java.util.Set;

/**
 *  set operate
 * @author ferdi email:ferdi@blueferdi.com
 * @since 2013-06-28 15:00:00
 * @version 0.0.1
 */
public interface SetCommands<K>
{
     /**
     * 取得<tt>key</tt>的Set中不存在于<tt>otherKey</tt>的Set的数据<br />
     * 例如： <code>
     * key1 = {a,b,c,d}
     * key2 = {c,d}
     * difference(key1, key2) = {a,b}
     * </code>
     *
     * @param namespace
     * @param key
     * @param otherKey
     * @return
     */
     <V> Set<V> difference(int namespace, K key, K otherKey);

    /**
     * 取得<tt>key</tt>的Set中不存在于<tt>otherKeys</tt>的Set的数据<br />
     * 例如： <code>
     * key1 = {a,b,c,d}
     * key2 = {c}
     * key3 = {a,d}
     * otherKeys = {key2,key3}
     * difference(key1, otherKeys) = {b,d}
     * </code>
     *
     * @param namespace
     * @param key
     * @param otherKeys
     * @return
     */
     <V> Set<V> difference(int namespace, K key, Collection<K> otherKeys);

    /**
     * 取得<tt>key</tt>的Set中不存在于<tt>otherKey</tt>的Set的数据，将数据存到<tt>destKey</tt> 例如：
     * <code>
     * key1 = {a,b,c,d}
     * key2 = {a,c}
     * destKey = differenceAndStore(key1, key2)
     * destKey = {b,d}
     * </code>
     *
     * @param namespace
     * @param key
     * @param otherKey
     * @param destKey
     */
     void differenceAndStore(int namespace, K key, K otherKey, K destKey);

    /**
     * 取得<tt>key</tt>的Set中不存在于<tt>otherKeys</tt>的Set的数据，将数据存到<tt>destKey</tt>
     * 例如： <code>
     * key1 = {a,b,c,d}
     * key2 = {a}
     * key3 = {a,c}
     * otherKeys = {key2,key3}
     * destKey = differenceAndStore(key1, otherKeys)
     * destKey = {b,d}
     * </code>
     *
     * @param namespace
     * @param key
     * @param otherKeys
     * @param destKey
     */
     void differenceAndStore(int namespace, K key, Collection<K> otherKeys, K destKey);

    /**
     * 取得<tt>key</tt>的Set中<tt>otherKey</tt>的Set中交集的数据 例如： <code>
     * key1 = {a,b,c,d}
     * key2 = {a,c}
     * intersect(key1, key2) = {a,c}
     * </code>
     *
     * @param namespace
     * @param key
     * @param otherKey
     * @return
     */
     <V> Set<V> intersect(int namespace, K key, K otherKey);

    /**
     * 取得<tt>key</tt>的Set中<tt>otherKeys</tt>的Set中交集的数据 例如： <code>
     * key1 = {a,b,c,d}
     * key2 = {c}
     * key3 = {a,c,e}
     * otherKeys = {key2,key3}
     * intersect(key1, otherKeys) = {c}
     * </code>
     *
     * @param namespace
     * @param key
     * @param otherKeys
     * @return
     */
     <V> Set<V> intersect(int namespace, K key, Collection<K> otherKeys);

    /**
     * 取得<tt>key</tt>的Set中<tt>otherKey</tt>的Set中交集的数据，将数据放入<tt>destKey</tt> 例如：
     * <code>
     * key1 = {a,b,c,d}
     * key2 = {a,c}
     * intersectAndStore(key1, key2, destKey)
     * destKey = {a,c}
     * </code>
     *
     * @param namespace
     * @param key
     * @param otherKey
     * @param destKey
     */
     void intersectAndStore(int namespace, K key, K otherKey, K destKey);

    /**
     * 取得<tt>key</tt>的Set中<tt>otherKeys</tt>的Set中交集数据，将数据放入<tt>destKey</tt> 例如：
     * <code>
     * key1 = {a,b,c,d}
     * key2 = {c}
     * key3 = {a,c,e}
     * otherKeys = {key2,key3}
     * intersectAndStore(key1, otherKeys, destKey)
     * destKey = {c}
     * </code>
     *
     * @param namespace
     * @param key
     * @param otherKeys
     * @param destKey
     */
     void intersectAndStore(int namespace, K key, Collection<K> otherKeys, K destKey);

    /**
     * 取得<tt>key</tt>的Set和<tt>otherKey</tt>的Set的并集数据 例如： <code>
     * key1 = {a,b,c}
     * key2 = {c,d}
     * union(key1, key2) = {a,b,c,d}
     * </code>
     *
     * @param namespace
     * @param key
     * @param otherKey
     * @return
     */
     <V> Set<V> union(int namespace, K key, K otherKey);

    /**
     * 取得<tt>key</tt>的Set和<tt>otherKeys</tt>的Set的并集数据 例如： <code>
     * key1 = {a,b,c}
     * key2 = {c,d}
     * key3 = {a,c,e}
     * otherKeys = {key2,key3}
     * union(key1, otherKeys) = {a,b,c,d,e}
     * </code>
     *
     * @param namespace
     * @param key
     * @param otherKeys
     * @return
     */
     <V> Set<V> union(int namespace, K key, Collection<K> otherKeys);

    /**
     * 取得<tt>key</tt>的Set和<tt>otherKey</tt>的Set的并集数据，将数据存入<tt>destKey</tt> 例如：
     * <code>
     * key1 = {a,b,c}
     * key2 = {c,d}
     * unionAndStore(key1, key2, destKey)
     * destKey = {a,b,c,d}
     * </code>
     *
     * @param namespace
     * @param key
     * @param otherKey
     * @param destKey
     */
     void unionAndStore(int namespace, K key, K otherKey, K destKey);

    /**
     * 取得<tt>key</tt>的Set和<tt>otherKeys</tt>的Set的并集数据，将数据存入<tt>destKey</tt> 例如：
     * <code>
     * key1 = {a,b,c}
     * key2 = {c,d}
     * key3 = {a,c,e}
     * otherKeys = {key2,key3}
     * union(key1, otherKeys, destKey)
     * destKey = {a,b,c,d,e}
     * </code>
     *
     * @param namespace
     * @param key
     * @param otherKeys
     * @param destKey
     */
     void unionAndStore(int namespace, K key, Collection<K> otherKeys, K destKey);

    /**
     * 向<tt>key</tt>的Set增加一个数据
     *
     * @param namespace
     * @param key
     * @param value
     * @return
     */
     <V> Long add(int namespace, K key, V... value);

    /**
     * 判断<tt>o</tt>是否为<tt>key</tt>的Set中的数据
     *
     * @param namespace
     * @param key
     * @param o
     * @return
     */
     Boolean isMember(int namespace, K key, Object o);

    /**
     * 取得所有成员
     *
     * @param namespace
     * @param key
     * @return
     */
     <V> Set<V> members(int namespace, K key);

    /**
     * 移动<tt>key</tt>的Set中的<tt>value</tt>到<tt>destKey</tt>
     *
     * @param namespace
     * @param key
     * @param value
     * @param destKey
     * @return
     */
     <V> Boolean move(int namespace, K key, V value, K destKey);

    /**
     * 随机取得一个数据
     *
     * @param namespace
     * @param key
     * @return
     */
     <V> V randomMember(int namespace, K key);

    /**
     * 删除一个数据
     *
     * @param namespace
     * @param key
     * @param o
     * @return
     */
     Long remove(int namespace, K key, Object... o);

    /**
     * 删除并取出第一个数据
     *
     * @param namespace
     * @param key
     * @return
     */
     <V> V pop(int namespace, K key);

    /**
     * 取得Set大小
     *
     * @param namespace
     * @param key
     * @return
     */
     Long size(int namespace, K key);
}
