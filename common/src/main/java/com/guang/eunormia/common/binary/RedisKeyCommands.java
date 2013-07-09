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
import com.guang.eunormia.common.util.SortParams;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jun 28, 2013 5:11:58 PM
 * @version 0.0.1
*/
public interface RedisKeyCommands 
{
    @Process(Policy.READ)
    Boolean exists(byte[] key);

    @Process(Policy.WRITE)
    Long del(byte[]... keys);

    @Process(Policy.READ)
    String type(byte[] key);

    @Process(Policy.READ)
    Set<byte[]> keys(byte[] pattern);

    @Process(Policy.READ)
    byte[] randomKey();

    @Process(Policy.WRITE)
    Boolean rename(byte[] oldName, byte[] newName);

    @Process(Policy.WRITE)
    Boolean renameNX(byte[] oldName, byte[] newName);

    @Process(Policy.WRITE)
    Boolean expire(byte[] key, long seconds);

    @Process(Policy.WRITE)
    Boolean expireAt(byte[] key, long unixTime);

    @Process(Policy.WRITE)
    Boolean persist(byte[] key);

    @Process(Policy.WRITE)
    Boolean move(byte[] key, int dbIndex);

    @Process(Policy.READ)
    Long ttl(byte[] key);

    @Process(Policy.READ)
    List<byte[]> sort(byte[] key, SortParams params);

    @Process(Policy.READ)
    Long sort(byte[] key, SortParams params, byte[] storeKey);
}
