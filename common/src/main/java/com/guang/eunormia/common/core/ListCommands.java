/**
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation.
 *
 */
package com.guang.eunormia.common.core;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * list operator
 * @author ferdi email:ferdi@blueferdi.com
 * @since 2013-06-28 15:00:00
 * @version 0.0.1
 */
public interface ListCommands<K>
{
    /**
     * 取得List中从<tt>start</tt>到<tt>end</tt>的数据<br />
     * 包括<tt>start</tt>和<tt>end</tt>本身指向的数据<br />
     * 例如：<code>range(1,somekey, 0, 10)</code>取得的是11个数据<br />
     * <tt>start</tt>和<tt>end</tt>可为负，表示队列倒数的计数<br />
     * 例如：<code>range(1,somekey, -2, -1)</code>取得的是倒数2个数据
     * @param namespace
     * @param key
     * @param start
     * @param end
     * @return
     */
     <V> List<V> range(int namespace, K key, long start, long end);

    /**
     * 截断List，保留从<tt>start</tt>开始到<tt>end</tt>的数据
     * @param namespace
     * @param key
     * @param start
     * @param end
     */
     void trim(int namespace, K key, long start, long end);

    /**
     * 取得List大小
     * @param namespace
     * @param key
     * @return
     */
     Long size(int namespace, K key);

    /**
     * 从队列头部插入一个数据
     * @param namespace
     * @param key
     * @param value
     * @return
     */
     <V> Long leftPush(int namespace, K key, V... value);

    /**
     * 从对ie头部插入一个数据，仅当该<tt>key</tt>不存在才插入数据
     * @param namespace
     * @param key
     * @param value
     * @return
     */
     <V> Long leftPushIfPresent(int namespace, K key, V value);

    /**
     * 在<tt>pivot</tt>之前插入一个数据
     * @param namespace
     * @param key
     * @param pivot
     * @param value
     * @return
     */
     <V> Long leftInsert(int namespace, K key, V pivot, V value);

    /**
     * 在队列尾插入一个数据
     * @param namespace
     * @param key
     * @param value
     * @return
     */
     <V> Long rightPush(int namespace, K key, V... value);

    /**
     * 在队列尾插入一个数据，仅当<tt>key</tt>不存在才插入数据
     * @param namespace
     * @param key
     * @param value
     * @return
     */
     <V> Long rightPushIfPresent(int namespace, K key, V value);

    /**
     * 在<tt>pivot</tt>之后插入一个数据
     * @param namespace
     * @param key
     * @param pivot
     * @param value
     * @return
     */
     <V> Long rightInsert(int namespace, K key, V pivot, V value);

    /**
     * 更新位置<tt>index</tt>的值
     * @param namespace
     * @param key
     * @param index
     * @param value
     */
     <V> void set(int namespace, K key, long index, V value);

    /**
     * 删除List中<tt>i</tt>值为<tt>value</tt>数据
     * <ul>
     *  <li>i大于0：从队列头开始删除i个数据</li>
     *  <li>i小于0：从队列尾开始删除i个数据</li>
     *  <li>i等于0：删除所有值为<tt>value</tt>的数据</li>
     * </ul>
     * @param namespace
     * @param key
     * @param i
     * @param value
     * @return
     */
     Long remove(int namespace, K key, long i, Object value);

    /**
     * 取得位于<tt>index</tt>处的数据
     * @param namespace
     * @param key
     * @param index
     * @return
     */
     <V> V index(int namespace, K key, long index);

    /**
     * 删除并取出队列头部的一个数据，如果不存在立即返回null
     * @param namespace
     * @param key
     * @return
     */
     <V> V leftPop(int namespace, K key);

    /**
     * 删除并取出队列头部的一个数据，如果不存在则等待指定的超时时间<br />
     * <tt>timeout</tt>为0则表示永不超时
     * @param namespace
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
     <V> V leftPop(int namespace, K key, long timeout, TimeUnit unit);

    /**
     * 删除并取出队列尾部的一个数据，如果不存在则立即返回null
     * @param namespace
     * @param key
     * @return
     */
     <V> V rightPop(int namespace, K key);

    /**
     * 删除并取出队列尾部的一个数据，如果不存在则等待指定的超时时间<br />
     * <tt>timeout</tt>为0则表示永不超时
     * @param namespace
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
     <V> V rightPop(int namespace, K key, long timeout, TimeUnit unit);

    /**
     * 将<tt>sourceKey</tt>的尾部的一个数据删除并插入到<tt>destinationKey</tt>的头部，并且将该数据返回，如果数据不存在则立即返回null
     * @param namespace
     * @param sourceKey
     * @param destinationKey
     * @return
     */
     <V> V rightPopAndLeftPush(int namespace, K sourceKey, K destinationKey);

    /**
     * 将<tt>sourceKey</tt>的尾部的一个数据删除并插入到<tt>destinationKey</tt>的头部，并且将该数据返回，如果数据不存在则等待指定时间
     * <tt>timeout</tt>为0则表示永不超时
     * @param namespace
     * @param sourceKey
     * @param destinationKey
     * @param timeout
     * @param unit
     * @return
     */
     <V> V rightPopAndLeftPush(int namespace, K sourceKey, K destinationKey, long timeout, TimeUnit unit);
}
