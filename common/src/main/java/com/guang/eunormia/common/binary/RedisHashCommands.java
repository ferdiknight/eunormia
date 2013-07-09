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
import java.util.Set;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jun 28, 2013 5:10:53 PM
 * @version 0.0.1
*/
public interface RedisHashCommands 
{
    @Process(Policy.WRITE)
    Boolean hSet(byte[] key, byte[] field, byte[] value);

    @Process(Policy.WRITE)
    Boolean hSetNX(byte[] key, byte[] field, byte[] value);

    @Process(Policy.READ)
    byte[] hGet(byte[] key, byte[] field);

    @Process(Policy.READ)
    List<byte[]> hMGet(byte[] key, byte[]... fields);

    @Process(Policy.WRITE)
    Boolean hMSet(byte[] key, Map<byte[], byte[]> hashes);

    @Process(Policy.WRITE)
    Long hIncrBy(byte[] key, byte[] field, long delta);

    @Process(Policy.READ)
    Boolean hExists(byte[] key, byte[] field);

    @Process(Policy.WRITE)
    Long hDel(byte[] key, byte[]... field);

    @Process(Policy.READ)
    Long hLen(byte[] key);

    @Process(Policy.READ)
    Set<byte[]> hKeys(byte[] key);

    @Process(Policy.READ)
    List<byte[]> hVals(byte[] key);

    @Process(Policy.READ)
    Map<byte[], byte[]> hGetAll(byte[] key);
}
