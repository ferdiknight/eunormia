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

import com.guang.eunormia.common.EunormiaConnectionException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jun 28, 2013 5:32:15 PM
 * @version 0.0.1
*/
public class RedisInputStream extends FilterInputStream
{
    protected final byte buf[];

    protected int count, limit;

    public RedisInputStream(InputStream in, int size) {
        super(in);
        if (size <= 0) {
            throw new IllegalArgumentException("Buffer size <= 0");
        }
        buf = new byte[size];
    }

    public RedisInputStream(InputStream in) {
        this(in, 8192);
    }

    public byte readByte() throws IOException {
        if (count == limit) {
            fill();
        }

        return buf[count++];
    }

    public String readLine() {
        int b;
        byte c;
        StringBuilder sb = new StringBuilder();

        try {
            while (true) {
                if (count == limit) {
                    fill();
                }
                if (limit == -1)
                    break;

                b = buf[count++];
                if (b == '\r') {
                    if (count == limit) {
                        fill();
                    }

                    if (limit == -1) {
                        sb.append((char) b);
                        break;
                    }

                    c = buf[count++];
                    if (c == '\n') {
                        break;
                    }
                    sb.append((char) b);
                    sb.append((char) c);
                } else {
                    sb.append((char) b);
                }
            }
        } catch (IOException e) {
            throw new EunormiaConnectionException(e);
        }
        String reply = sb.toString();
        if (reply.length() == 0) {
            throw new EunormiaConnectionException("It seems like server has closed the connection.");
        }
        return reply;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (count == limit) {
            fill();
            if (limit == -1)
                return -1;
        }
        final int length = Math.min(limit - count, len);
        System.arraycopy(buf, count, b, off, length);
        count += length;
        return length;
    }

    private void fill() throws IOException {
        limit = in.read(buf);
        count = 0;
    }
}
