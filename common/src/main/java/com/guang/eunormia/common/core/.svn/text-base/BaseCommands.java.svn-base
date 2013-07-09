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

import com.guang.eunormia.common.Cluster;
import com.guang.eunormia.common.EunormiaDataException;
import com.guang.eunormia.common.EunormiaException;
import com.guang.eunormia.common.binary.RedisCommands;
import com.guang.eunormia.common.binary.RedisSortedSetCommands.Tuple;
import com.guang.eunormia.common.serializer.EunormiaSerializer;
import com.guang.eunormia.common.serializer.HessianSerializer;
import com.guang.eunormia.common.serializer.SerializationUtils;
import com.guang.eunormia.common.serializer.StringEunormiaSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 10:25:53 AM
 * @version 0.0.1
*/
public abstract class BaseCommands 
{
    public static final byte[] PART = new byte[] { ':' };
    protected EunormiaSerializer stringSerializer = new StringEunormiaSerializer();
    private EunormiaSerializer keySerializer = new HessianSerializer();
    private EunormiaSerializer valueSerializer = keySerializer;
    private EunormiaSerializer hashKeySerializer = keySerializer;
    private EunormiaSerializer hashValueSerializer = keySerializer;

    protected Cluster commandsProvider;

    public void setCommandsProvider(Cluster commandsProvider) {
        this.commandsProvider = commandsProvider;
    }

    public EunormiaSerializer getStringSerializer() {
        return stringSerializer;
    }

    public void setStringSerializer(EunormiaSerializer stringSerializer) {
        this.stringSerializer = stringSerializer;
    }

    public EunormiaSerializer getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(EunormiaSerializer keySerializer) {
        this.keySerializer = keySerializer;
    }

    public EunormiaSerializer getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(EunormiaSerializer valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public EunormiaSerializer getHashKeySerializer() {
        return hashKeySerializer;
    }

    public void setHashKeySerializer(EunormiaSerializer hashKeySerializer) {
        this.hashKeySerializer = hashKeySerializer;
    }

    public EunormiaSerializer getHashValueSerializer() {
        return hashValueSerializer;
    }

    public void setHashValueSerializer(EunormiaSerializer hashValueSerializer) {
        this.hashValueSerializer = hashValueSerializer;
    }

    protected byte[] rawKey(int namespace, Object key) {
        if (key == null) {
            return null;
        }
        byte[] prefix = rawString(String.valueOf(namespace));
        byte[] bytekey = keySerializer.serialize(key);
        byte[] result = new byte[prefix.length + bytekey.length + 1];
        System.arraycopy(prefix, 0, result, 0, prefix.length);
        System.arraycopy(PART, 0, result, prefix.length, 1);
        System.arraycopy(bytekey, 0, result, prefix.length + 1, bytekey.length);
        return result;
    }

    protected byte[] removeNamespaceFromKey(byte[] key) {
        int i = 0;
        while (key[i] != PART[0]) {
            i++;
        }
        i++;
        byte[] result = new byte[key.length - i];
        System.arraycopy(key, i, result, 0, key.length - i);
        return result;
    }

    protected byte[] rawString(String key) {
        return stringSerializer.serialize(key);
    }

    protected byte[] rawLong(Long value) {
        return rawString(String.valueOf(value));
    }

    protected byte[] rawValue(Object value) {
        if (value == null) {
            throw new EunormiaDataException("value can not be null.");
        }
        return valueSerializer.serialize(value);
    }

    protected byte[][] rawValues(Object... value) {
        if (value == null) {
            throw new EunormiaDataException("value can not be null.");
        }
        byte[][] result = new byte[value.length][];
        int i = 0;
        for(Object v : value) {
            result[i++] = valueSerializer.serialize(v);
        }
        return result;
    }

    protected <HK> byte[] rawHashKey(HK hashKey) {
        if (hashKey == null) {
            throw new EunormiaDataException("non null hash key required");
        }
        return hashKeySerializer.serialize(hashKey);
    }

    protected <HK> byte[][] rawHashKeys(HK... hashKey) {
        if (hashKey == null) {
            throw new EunormiaDataException("non null hash key required");
        }
        final byte[][] rawkeys = new byte[hashKey.length][];
        int i = 0;
        for(HK key : hashKey) {
            rawkeys[i++] = hashKeySerializer.serialize(key);
        }
        return rawkeys;
    }

    protected <HV> byte[] rawHashValue(HV value) {
        return hashValueSerializer.serialize(value);
    }

    protected <K> byte[][] rawKeys(int namespace, K key, K otherKey) {
        final byte[][] rawKeys = new byte[2][];
        rawKeys[0] = rawKey(namespace, key);
        rawKeys[1] = rawKey(namespace, key);
        return rawKeys;
    }

    protected <K> byte[][] rawKeys(int namespace, Collection<K> keys) {
        return rawKeys(namespace, null, keys);
    }

    protected <K> byte[][] rawKeys(int namespace, K key, Collection<K> keys) {
        final byte[][] rawKeys = new byte[keys.size() + (key != null ? 1 : 0)][];
        int i = 0;
        if (key != null) {
            rawKeys[i++] = rawKey(namespace, key);
        }
        for (K k : keys) {
            rawKeys[i++] = rawKey(namespace, k);
        }
        return rawKeys;
    }

    protected <V> Set<V> deserializeValues(Set<byte[]> rawValues) {
        return SerializationUtils.deserialize(rawValues, valueSerializer);
    }

    protected <V> List<V> deserializeValues(List<byte[]> rawValues) {
        return SerializationUtils.deserialize(rawValues, valueSerializer);
    }

    protected List<String> deserializeStrings(List<byte[]> rawValues) {
        return SerializationUtils.deserialize(rawValues, stringSerializer);
    }

    protected List<Long> deserializeLongs(List<byte[]> rawValues) {
        List<String> list = SerializationUtils.deserialize(rawValues, stringSerializer);
        List<Long> result = new ArrayList<Long>();
        for (String l : list) {
            if (l == null) {
                result.add(0l);
            } else {
                result.add(Long.parseLong(l));
            }
        }
        return result;
    }

    protected <T> Set<T> deserializeHashKeys(Set<byte[]> rawKeys) {
        return SerializationUtils.deserialize(rawKeys, hashKeySerializer);
    }

    protected <T> List<T> deserializeHashValues(List<byte[]> rawValues) {
        return SerializationUtils.deserialize(rawValues, hashValueSerializer);
    }

    protected <HK, HV> Map<HK, HV> deserializeHashMap(Map<byte[], byte[]> entries) {
        if (entries == null) {
            return null;
        }
        Map<HK, HV> map = new LinkedHashMap<HK, HV>(entries.size());
        for (Map.Entry<byte[], byte[]> entry : entries.entrySet()) {
            map.put((HK) deserializeHashKey(entry.getKey()), (HV) deserializeHashValue(entry.getValue()));
        }
        return map;
    }

    protected <HK> Map<HK, Double> deserializeTruble(Set<Tuple> tuples) {
        if (tuples == null) {
            return null;
        }
        Map<HK, Double> map = new LinkedHashMap<HK, Double>(tuples.size());
        for (Tuple t : tuples) {
            map.put((HK) deserializeKey(t.getValue()), t.getScore());
        }
        return map;
    }

    protected <K> K deserializeKey(byte[] value) {
        return (K) keySerializer.deserialize(value);
    }

    protected <V> V deserializeValue(byte[] value) {
        return (V) valueSerializer.deserialize(value);
    }

    protected String deserializeString(byte[] value) {
        return (String) stringSerializer.deserialize(value);
    }

    protected Long deserializeLong(byte[] value) {
        String s = deserializeString(value);
        if (s == null) {
            return 0L;
        }
        return Long.parseLong(deserializeString(value));
    }

    protected <HK> HK deserializeHashKey(byte[] value) {
        return (HK) hashKeySerializer.deserialize(value);
    }

    protected <HV> HV deserializeHashValue(byte[] value) {
        return (HV) hashValueSerializer.deserialize(value);
    }

    protected Object doInEunormia(int db, EunormiaBlock block) {
        if (block.commands == null) {
            throw new EunormiaException("RedisCommands is null.You must set a redisCommands object before using me!");
        }
        return block.execute();
    }

    protected abstract class EunormiaBlock {
        protected RedisCommands commands;

        public EunormiaBlock(RedisCommands redisCommands) {
            this.commands = redisCommands;
        }

        public abstract Object execute();
    }
}
