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

import com.guang.eunormia.common.config.Process;
import com.guang.eunormia.common.config.Process.Policy;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 11:18:40 AM
 * @version 0.0.1
*/
public interface RedisPubSubCommands 
{
    @Process(Policy.READ)
    boolean isSubscribed();

    @Process(Policy.WRITE)
    Long publish(byte[] channel, byte[] message);

    @Process(Policy.WRITE)
    Boolean subscribe(MessageListener listener, byte[]... channels);

    @Process(Policy.WRITE)
    Boolean pSubscribe(MessageListener listener, byte[]... patterns);
}
