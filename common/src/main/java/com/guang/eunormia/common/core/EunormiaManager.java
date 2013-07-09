/*
 * Copyright (C) 2013 ferdi
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 */
package com.guang.eunormia.common.core;

import com.guang.eunormia.common.binary.DataType;
import com.guang.eunormia.common.serializer.EunormiaSerializer;
import com.guang.eunormia.common.util.SortParams;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 3:59:39 PM
 * @version 0.0.1
 */
public interface EunormiaManager<K>
{

    Boolean hasKey(int namespace, K key);

    void delete(int namespace, K key);

    void delete(int namespace, Collection<K> keys);

    DataType type(int namespace, K key);

    Set<K> keys(int namespace, String pattern);

    void rename(int namespace, K oldKey, K newKey);

    Boolean renameIfAbsent(int namespace, K oldKey, K newKey);

    Boolean expire(int namespace, K key, long timeout, TimeUnit unit);

    Boolean expireAt(int namespace, K key, Date date);

    Boolean persist(int namespace, K key);

    Long getExpire(int namespace, K key);

    AtomicCommands<K> getAtomicCommands();

    StringCommands getStringCommands();

    ValueCommands<K> getValueCommands();

    HashCommands<K> getHashCommands();

    ListCommands<K> getListCommands();

    SetCommands<K> getSetCommands();

    SortedSetCommands<K> getZSetCommands();

    <V> List<V> sort(int namespace, K key, SortParams params);

    Long sort(int namespace, K key, SortParams query, K storeKey);

    EunormiaSerializer<?> getValueSerializer();

    EunormiaSerializer<K> getKeySerializer();

    void destroy();
}
