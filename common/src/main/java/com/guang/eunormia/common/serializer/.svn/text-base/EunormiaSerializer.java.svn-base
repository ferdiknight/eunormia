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

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jun 28, 2013 5:16:59 PM
 * @version 0.0.1
*/
public interface EunormiaSerializer<T>
{
    /**
     * Serialize the given object to binary data.
     * 
     * @param t
     *            object to serialize
     * @return the equivalent binary data
     */
    byte[] serialize(T t) throws SerializationException;

    /**
     * Deserialize an object from the given binary data.
     * 
     * @param bytes
     *            object binary representation
     * @return the equivalent object instance
     */
    T deserialize(byte[] bytes) throws SerializationException;
}
