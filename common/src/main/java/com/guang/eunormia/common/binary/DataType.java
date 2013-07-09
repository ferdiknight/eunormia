/*
 * Copyright (C) 2013 ferdi
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 */

package com.guang.eunormia.common.binary;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * the data types support now
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jun 28, 2013 5:02:01 PM
 * @version 0.0.1
*/
public enum DataType 
{
    NONE("none"), VALUE("string"), LIST("list"), SET("set"), ZSET("zset"), HASH("hash");

    private static final Map<String, DataType> codeLookup = new ConcurrentHashMap<String, DataType>(6);

    static {
        for (Iterator<DataType> it = EnumSet.allOf(DataType.class).iterator(); it.hasNext();)
        {
            DataType type = it.next();
            codeLookup.put(type.code, type);
        }

    }

    private final String code;

    DataType(String name) {
        this.code = name;
    }

    public String code() {
        return code;
    }

    public static DataType fromCode(String code) {
        DataType data = codeLookup.get(code);
        if (data == null)
            throw new IllegalArgumentException("unknown data type code");
        return data;
    }
}
