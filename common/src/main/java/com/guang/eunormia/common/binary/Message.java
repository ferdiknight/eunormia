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

import java.io.Serializable;
import java.util.Arrays;

/**
 * message object
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jun 28, 2013 5:04:33 PM
 * @version 0.0.1
*/
public class Message implements Serializable
{
    private final byte[] channel;
    private final byte[] body;

    public Message(byte[] channel, byte[] body) {
        this.body = body;
        this.channel = channel;
    }

    public byte[] getChannel() {
        return (channel != null ? channel.clone() : null);
    }

    public byte[] getBody() {
        return (body != null ? body.clone() : null);
    }

    @Override
    public String toString() {
        return Arrays.toString(body);
    }
}
