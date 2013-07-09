/*
 * Copyright (C) 2013 ferdi
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 */

package com.guang.eunormia.common.util;

import com.guang.eunormia.common.EunormiaDataException;
import com.guang.eunormia.common.EunormiaException;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jun 28, 2013 5:30:05 PM
 * @version 0.0.1
*/
public class SafeEncoder 
{
    public static byte[] encode(final String str) {
        try {
            if (str == null) {
                throw new EunormiaDataException("value sent to redis cannot be null");
            }
            return str.getBytes(Protocol.CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new EunormiaException(e);
        }
    }

    public static String encode(final byte[] data) {
        try {
            return new String(data, Protocol.CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new EunormiaException(e);
        }
    }
}
