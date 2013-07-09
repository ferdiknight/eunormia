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
 * SortedSet operate
 * @author ferdi email:ferdi@blueferdi.com
 * @since 2013-06-28 15:00:00
 * @version 0.0.1
 */
public interface SortedSetCommands<K>
{
    /**
     * 取得<tt>key</tt>和<tt>otherKey</tt>的交集，将结果存入<tt>destKey</tt>
     *
     * @param namespace
     * @param key
     * @param otherKey
     * @param destKey
     */
    void intersectAndStore(int namespace, K key, K otherKey, K destKey);

    /**
     * 取得<tt>key</tt>和<tt>otherKeys</tt>的交集，将结果存入<tt>destKey</tt>
     *
     * @param namespace
     * @param key
     * @param otherKeys
     * @param destKey
     */
    void intersectAndStore(int namespace, K key, Collection<K> otherKeys, K destKey);

    /**
     * 取得<tt>key</tt>和<tt>otherKey</tt>的并集，将结果存入<tt>destKey</tt>
     *
     * @param namespace
     * @param key
     * @param otherKey
     * @param destKey
     */
    void unionAndStore(int namespace, K key, K otherKey, K destKey);

    /**
     * 取得<tt>key</tt>和<tt>otherKeys</tt>的并集，将结果存入<tt>destKey</tt>
     *
     * @param namespace
     * @param key
     * @param otherKeys
     * @param destKey
     */
    void unionAndStore(int namespace, K key, Collection<K> otherKeys, K destKey);

    /**
     * 从权重最低的数据开始，取得位置从<tt>start</tt>到<tt>end</tt>的数据
     *
     * @param namespace
     * @param key
     * @param start
     * @param end
     * @return
     */
    <V> Set<V> range(int namespace, K key, long start, long end);

    /**
     * 从权重最低的数据开始，取得位置从<tt>start</tt>到<tt>end</tt>的数据
     *
     * @param namespace
     * @param key
     * @param start
     * @param end
     * @return
     */
    <V> Map<V, Double> rangeWithScore(int namespace, K key, long start, long end);

    /**
     * 取得权重从<tt>min</tt>到<tt>max</tt>的数据
     *
     * @param namespace
     * @param key
     * @param min
     * @param max
     * @return
     */
    <V> Set<V> rangeByScore(int namespace, K key, double min, double max);

    /**
     * 取得权重从<tt>min</tt>到<tt>max</tt>的数据
     *
     * @param namespace
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    <V> Set<V> rangeByScore(int namespace, K key, double min, double max, int offset, int count);

    /**
     * 取得权重从<tt>min</tt>到<tt>max</tt>的数据
     *
     * @param namespace
     * @param key
     * @param min
     * @param max
     * @return
     */
    <V> Set<V> reverseRangeByScore(int namespace, K key, double min, double max);

    /**
     * 取得权重从<tt>min</tt>到<tt>max</tt>的数据
     *
     * @param namespace
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    <V> Set<V> reverseRangeByScore(int namespace, K key, double min, double max, int offset, int count);

    /**
     * 取得权重从<tt>min</tt>到<tt>max</tt>的数据
     *
     * @param namespace
     * @param key
     * @param min
     * @param max
     * @return
     */
    <V> Map<V, Double> rangeByScoreWithScore(int namespace, K key, double min, double max);

    /**
     * 从权重最高的数据开始，取得位置从<tt>start</tt>到<tt>end</tt>的数据
     *
     * @param namespace
     * @param key
     * @param start
     * @param end
     * @return
     */
    <V> Set<V> reverseRange(int namespace, K key, long start, long end);

    /**
     * 从权重最高的数据开始，取得位置从<tt>start</tt>到<tt>end</tt>的数据
     *
     * @param namespace
     * @param key
     * @param start
     * @param end
     * @return
     */
    <V> Map<V, Double> reverseRangeWithScore(int namespace, K key, long start, long end);

    /**
     * 增加一个数据
     *
     * @param namespace
     * @param key
     * @param value
     * @param score
     * @return
     */
    <V> Boolean add(int namespace, K key, V value, double score);

    /**
     * 增加多个数据
     *
     * @param namespace
     * @param key
     * @param value
     * @param score
     * @return
     */
    <V> Long add(int namespace, K key, Map<V, Double> maps);

    /**
     * 增加一个数据的权重，并且返回更新后的权重值
     *
     * @param namespace
     * @param key
     * @param value
     * @param delta
     * @return
     */
    <V> Double incrementScore(int namespace, K key, V value, double delta);

    /**
     * 得到一个数据跟中权重的排名，排名最低的Rank值为0
     *
     * @param namespace
     * @param key
     * @param o
     * @return
     */
    Long rank(int namespace, K key, Object o);

    /**
     * 得到一个数据跟中权重的排名，排名最高的Rank值为0
     *
     * @param namespace
     * @param key
     * @param o
     * @return
     */
    Long reverseRank(int namespace, K key, Object o);

    /**
     * 得到一个数据的权重
     *
     * @param namespace
     * @param key
     * @param o
     * @return
     */
    Double score(int namespace, K key, Object o);

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
     * 删除坐标从<tt>start</tt>到<tt>end</tt>的所有数据
     *
     * @param namespace
     * @param key
     * @param start
     * @param end
     */
    void removeRange(int namespace, K key, long start, long end);

    /**
     * 删除权重值从<tt>min</tt>到<tt>max</tt>的所有数据
     *
     * @param namespace
     * @param key
     * @param min
     * @param max
     */
    void removeRangeByScore(int namespace, K key, double min, double max);

    /**
     * 取得权重值从<tt>min</tt>到<tt>max</tt>的数据数量
     *
     * @param namespace
     * @param key
     * @param min
     * @param max
     * @return
     */
    Long count(int namespace, K key, double min, double max);

    /**
     * 取得SortedSet大小
     *
     * @param namespace
     * @param key
     * @return
     */
    Long size(int namespace, K key);
}
