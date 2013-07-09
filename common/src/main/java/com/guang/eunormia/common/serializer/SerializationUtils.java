/*
 * Copyright (C) 2013 ferdi
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 */

package com.guang.eunormia.common.serializer;

import com.guang.eunormia.common.EunormiaDataException;
import com.guang.eunormia.common.EunormiaException;
import com.guang.eunormia.common.binary.RedisSortedSetCommands.Tuple;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jun 28, 2013 5:20:38 PM
 * @version 0.0.1
*/
public class SerializationUtils 
{
    public static final byte[] EMPTY_ARRAY = new byte[0];

    public static final String CHARSET = "UTF-8";

    public static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }

    public static byte[] serialize(final String str) {
        try {
            if (str == null) {
                throw new EunormiaDataException("value sent to redis cannot be null");
            }
            return str.getBytes(CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new EunormiaException(e);
        }
    }

    public static byte[][] serialize(List<String> strings, EunormiaSerializer<String> stringSerializer) {
        List<byte[]> raw = null;

        if (strings == null) {
            raw = Collections.emptyList();
        } else {
            raw = new ArrayList<byte[]>(strings.size());
            for (String key : strings) {
                raw.add(stringSerializer.serialize(key));
            }
        }
        return raw.toArray(new byte[raw.size()][]);
    }

    public static String deserialize(final byte[] data) {
        try {
            return new String(data, CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new EunormiaException(e);
        }
    }

    public static <T extends Collection<?>> T deserializeValues(Collection<byte[]> rawValues, Class<T> type, EunormiaSerializer<?> redisSerializer) {
        if (rawValues == null) {
            return null;
        }

        Collection<Object> values = (List.class.isAssignableFrom(type) ? new ArrayList<Object>(rawValues.size()) : new LinkedHashSet<Object>(rawValues.size()));
        for (byte[] bs : rawValues) {
            values.add(redisSerializer.deserialize(bs));
        }

        return (T) values;
    }

    public static <T> Set<T> deserialize(Set<byte[]> rawValues, EunormiaSerializer<T> redisSerializer) {
        return deserializeValues(rawValues, Set.class, redisSerializer);
    }

    public static <T> List<T> deserialize(List<byte[]> rawValues, EunormiaSerializer<T> redisSerializer) {
        return deserializeValues(rawValues, List.class, redisSerializer);
    }

    public static <T> Collection<T> deserialize(Collection<byte[]> rawValues, EunormiaSerializer<T> redisSerializer) {
        return deserializeValues(rawValues, List.class, redisSerializer);
    }

    public static <HK> Map<HK, Double> deserializeTruble(Set<Tuple> tuples, EunormiaSerializer<HK> redisSerializer) {
        if (tuples == null) {
            return null;
        }
        Map<HK, Double> map = new LinkedHashMap<HK, Double>(tuples.size());
        for (Tuple t : tuples) {
            map.put((HK) redisSerializer.deserialize(t.getValue()), t.getScore());
        }
        return map;
    }
}
