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
import java.util.List;
import java.util.Map;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 11:01:31 AM
 * @version 0.0.1
*/
public interface RedisValueCommands 
{
    @Process(Policy.READ)
    byte[] get(byte[] key);

    @Process(Policy.WRITE)
    byte[] getSet(byte[] key, byte[] value);

    @Process(Policy.READ)
    List<byte[]> mGet(byte[]... keys);

    @Process(Policy.WRITE)
    Boolean set(byte[] key, byte[] value);

    @Process(Policy.WRITE)
    Boolean setNX(byte[] key, byte[] value);

    @Process(Policy.WRITE)
    Boolean setEx(byte[] key, long seconds, byte[] value);

    @Process(Policy.WRITE)
    Boolean mSet(Map<byte[], byte[]> tuple);

    @Process(Policy.WRITE)
    Boolean mSetNX(Map<byte[], byte[]> tuple);

    @Process(Policy.WRITE)
    Long incr(byte[] key);

    @Process(Policy.WRITE)
    Long incrBy(byte[] key, long value);

    @Process(Policy.WRITE)
    Long decr(byte[] key);

    @Process(Policy.WRITE)
    Long decrBy(byte[] key, long value);

    @Process(Policy.WRITE)
    Long append(byte[] key, byte[] value);

    @Process(Policy.READ)
    byte[] getRange(byte[] key, long begin, long end);

    @Process(Policy.WRITE)
    Long setRange(byte[] key, byte[] value, long offset);

    @Process(Policy.READ)
    Boolean getBit(byte[] key, long offset);

    @Process(Policy.WRITE)
    Long setBit(byte[] key, long offset, boolean value);

    @Process(Policy.READ)
    Long strLen(byte[] key);
}
