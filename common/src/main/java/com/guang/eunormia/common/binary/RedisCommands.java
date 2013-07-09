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

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jun 28, 2013 4:49:37 PM
 * @version 0.0.1
*/
public interface RedisCommands extends RedisKeyCommands, RedisValueCommands, RedisListCommands, RedisSetCommands, RedisSortedSetCommands, RedisHashCommands, RedisConnectionCommands, RedisServerCommands
{

}
