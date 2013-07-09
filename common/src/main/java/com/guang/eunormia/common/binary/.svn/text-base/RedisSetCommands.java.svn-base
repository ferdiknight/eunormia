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
import java.util.Set;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 11:09:30 AM
 * @version 0.0.1
*/
public interface RedisSetCommands 
{
    @Process(Policy.WRITE)
    Long sAdd(byte[] key, byte[]... value);

    @Process(Policy.WRITE)
    Long sRem(byte[] key, byte[]... value);

    @Process(Policy.READ)
    byte[] sPop(byte[] key);

    @Process(Policy.WRITE)
    Boolean sMove(byte[] srcKey, byte[] destKey, byte[] value);

    @Process(Policy.READ)
    Long sCard(byte[] key);

    @Process(Policy.READ)
    Boolean sIsMember(byte[] key, byte[] value);

    @Process(Policy.READ)
    Set<byte[]> sInter(byte[]... keys);

    @Process(Policy.WRITE)
    Long sInterStore(byte[] destKey, byte[]... keys);

    @Process(Policy.READ)
    Set<byte[]> sUnion(byte[]... keys);

    @Process(Policy.WRITE)
    Long sUnionStore(byte[] destKey, byte[]... keys);

    @Process(Policy.READ)
    Set<byte[]> sDiff(byte[]... keys);

    @Process(Policy.WRITE)
    Long sDiffStore(byte[] destKey, byte[]... keys);

    @Process(Policy.READ)
    Set<byte[]> sMembers(byte[] key);

    @Process(Policy.READ)
    byte[] sRandMember(byte[] key);
}
