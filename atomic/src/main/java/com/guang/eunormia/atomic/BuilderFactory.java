/*
 * Copyright (C) 2013 ferdi
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 */

package com.guang.eunormia.atomic;

import com.guang.eunormia.common.binary.RedisSortedSetCommands.Tuple;
import com.guang.eunormia.common.util.SafeEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * a factory to product Builders
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 2:25:01 PM
 * @version 0.0.1
*/
public class BuilderFactory 
{
    /**
     * create Builder<Double>
     */
    public static final Builder<Double> DOUBLE = new Builder<Double>() {
        @Override
        public Double build(Object data) {
            return Double.valueOf(STRING.build(data));
        }

        @Override
        public String toString() {
            return "double";
        }
    };
    
    /**
     * create Builder<Boolean>
     */
    public static final Builder<Boolean> BOOLEAN = new Builder<Boolean>() {
        @Override
        public Boolean build(Object data) {
            return ((Long) data) == 1;
        }

        @Override
        public String toString() {
            return "boolean";
        }
    };
    
    /**
     * create Builder<byte[]>
     */
    public static final Builder<byte[]> BYTE_ARRAY = new Builder<byte[]>() {
        @Override
        public byte[] build(Object data) {
            return ((byte[]) data); // deleted == 1
        }

        @Override
        public String toString() {
            return "byte[]";
        }
    };

    /**
     * create Builder<Long>
     */
    public static final Builder<Long> LONG = new Builder<Long>() {
        @Override
        public Long build(Object data) {
            return (Long) data;
        }

        @Override
        public String toString() {
            return "long";
        }

    };
    
    /**
     * create Builder<String>
     */
    public static final Builder<String> STRING = new Builder<String>() {
        @Override
        public String build(Object data) {
            return data == null ? null : SafeEncoder.encode((byte[]) data);
        }

        @Override
        public String toString() {
            return "string";
        }

    };
    
    /**
     * create Builder<List<String>>
     */
    public static final Builder<List<String>> STRING_LIST = new Builder<List<String>>() {
        @SuppressWarnings("unchecked")
        @Override
        public List<String> build(Object data) {
            if (null == data) {
                return null;
            }
            List<byte[]> l = (List<byte[]>) data;
            final ArrayList<String> result = new ArrayList<String>(l.size());
            for (final byte[] barray : l) {
                if (barray == null) {
                    result.add(null);
                } else {
                    result.add(SafeEncoder.encode(barray));
                }
            }
            return result;
        }

        @Override
        public String toString() {
            return "List<String>";
        }

    };
    
    /**
     * create Builder<Map<String,String>>
     */
    public static final Builder<Map<String, String>> STRING_MAP = new Builder<Map<String, String>>() {
        @SuppressWarnings("unchecked")
        @Override
        public Map<String, String> build(Object data) {
            final List<byte[]> flatHash = (List<byte[]>) data;
            final Map<String, String> hash = new HashMap<String, String>();
            final Iterator<byte[]> iterator = flatHash.iterator();
            while (iterator.hasNext()) {
                hash.put(SafeEncoder.encode(iterator.next()), SafeEncoder.encode(iterator.next()));
            }

            return hash;
        }

        @Override
        public String toString() {
            return "Map<String, String>";
        }

    };
    
    /**
     * create Builder<Set<String>>
     */
    public static final Builder<Set<String>> STRING_SET = new Builder<Set<String>>() {
        @SuppressWarnings("unchecked")
        @Override
        public Set<String> build(Object data) {
            if (null == data) {
                return null;
            }
            List<byte[]> l = (List<byte[]>) data;
            final Set<String> result = new HashSet<String>(l.size());
            for (final byte[] barray : l) {
                if (barray == null) {
                    result.add(null);
                } else {
                    result.add(SafeEncoder.encode(barray));
                }
            }
            return result;
        }

        @Override
        public String toString() {
            return "Set<String>";
        }

    };

    /**
     * create Builder<List<byte[]>>
     */
    public static final Builder<List<byte[]>> BYTE_ARRAY_LIST = new Builder<List<byte[]>>() {
        @SuppressWarnings("unchecked")
        @Override
        public List<byte[]> build(Object data) {
            if (null == data) {
                return null;
            }
            List<byte[]> l = (List<byte[]>) data;

            return l;
        }

        @Override
        public String toString() {
            return "List<byte[]>";
        }
    };

    /**
     * create Builder<Set<byte[]>>
     */
    public static final Builder<Set<byte[]>> BYTE_ARRAY_ZSET = new Builder<Set<byte[]>>() {
        @SuppressWarnings("unchecked")
        @Override
        public Set<byte[]> build(Object data) {
            if (null == data) {
                return null;
            }
            List<byte[]> l = (List<byte[]>) data;
            final Set<byte[]> result = new LinkedHashSet<byte[]>(l);
            return result;
        }

        @Override
        public String toString() {
            return "ZSet<byte[]>";
        }
    };

    /**
     * create Builder<Set<String>>
     */
    public static final Builder<Set<String>> STRING_ZSET = new Builder<Set<String>>() {
        @SuppressWarnings("unchecked")
        @Override
        public Set<String> build(Object data) {
            if (null == data) {
                return null;
            }
            List<byte[]> l = (List<byte[]>) data;
            final Set<String> result = new LinkedHashSet<String>(l.size());
            for (final byte[] barray : l) {
                if (barray == null) {
                    result.add(null);
                } else {
                    result.add(SafeEncoder.encode(barray));
                }
            }
            return result;
        }

        @Override
        public String toString() {
            return "ZSet<String>";
        }

    };

    /**
     * create Builder<Set<Tuple>>
     */
    public static final Builder<Set<Tuple>> TUPLE_ZSET = new Builder<Set<Tuple>>() {
        @SuppressWarnings("unchecked")
        @Override
        public Set<Tuple> build(Object data) {
            if (null == data) {
                return null;
            }
            List<byte[]> l = (List<byte[]>) data;
            final Set<Tuple> result = new LinkedHashSet<Tuple>(l.size());
            Iterator<byte[]> iterator = l.iterator();
            while (iterator.hasNext()) {
                result.add(new Tuple(iterator.next(), Double.valueOf(SafeEncoder.encode(iterator.next()))));
            }
            return result;
        }

        @Override
        public String toString() {
            return "ZSet<Tuple>";
        }

    };

    /**
     * create Builder<Set<Tuple>>
     */
    public static final Builder<Set<Tuple>> TUPLE_ZSET_BINARY = new Builder<Set<Tuple>>() {
        @SuppressWarnings("unchecked")
        @Override
        public Set<Tuple> build(Object data) {
            if (null == data) {
                return null;
            }
            List<byte[]> l = (List<byte[]>) data;
            final Set<Tuple> result = new LinkedHashSet<Tuple>(l.size());
            Iterator<byte[]> iterator = l.iterator();
            while (iterator.hasNext()) {
                result.add(new Tuple(iterator.next(), Double.valueOf(SafeEncoder.encode(iterator.next()))));
            }

            return result;

        }

        @Override
        public String toString() {
            return "ZSet<Tuple>";
        }
    };

}
