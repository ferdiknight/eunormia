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

import java.nio.charset.Charset;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jun 28, 2013 5:19:17 PM
 * @version 0.0.1
*/
public class StringEunormiaSerializer implements EunormiaSerializer<String>
{

    private final Charset charset;

    public StringEunormiaSerializer() {
        this(Charset.forName(SerializationUtils.CHARSET));
    }

    public StringEunormiaSerializer(Charset charset) {
        this.charset = charset;
    }

    @Override
    public String deserialize(byte[] bytes) {
        return (bytes == null ? null : new String(bytes, charset));
    }

    @Override
    public byte[] serialize(String string) {
        return (string == null ? null : string.getBytes(charset));
    }

}
