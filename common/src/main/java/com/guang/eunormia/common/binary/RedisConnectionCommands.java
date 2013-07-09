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
 * @since Jun 28, 2013 5:09:33 PM
 * @version 0.0.1
 */
public interface RedisConnectionCommands
{

    @Process(Policy.WRITE)
    Boolean select(int dbIndex);

    @Process(Policy.WRITE)
    byte[] echo(byte[] message);

    @Process(Policy.WRITE)
    Boolean ping();

    @Process(Policy.WRITE)
    Boolean connect();

    @Process(Policy.WRITE)
    Boolean disconnect();

    @Process(Policy.WRITE)
    Boolean quit();

    @Process(Policy.WRITE)
    Boolean auth(String password);
}
