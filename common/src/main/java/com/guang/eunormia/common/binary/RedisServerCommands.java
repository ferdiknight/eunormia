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
import java.util.Properties;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 11:13:30 AM
 * @version 0.0.1
*/
public interface RedisServerCommands 
{
    @Process(Policy.WRITE)
    Boolean bgWriteAof();

    @Process(Policy.WRITE)
    Boolean bgSave();

    @Process(Policy.READ)
    Long lastSave();

    @Process(Policy.WRITE)
    Boolean save();

    @Process(Policy.READ)
    Long dbSize();

    @Process(Policy.WRITE)
    Boolean flushDb();

    @Process(Policy.WRITE)
    Boolean flushAll();

    @Process(Policy.READ)
    Properties info();

    @Process(Policy.WRITE)
    Boolean shutdown();

    @Process(Policy.READ)
    List<String> getConfig(String pattern);

    @Process(Policy.WRITE)
    Boolean setConfig(String param, String value);

    @Process(Policy.WRITE)
    Boolean resetConfigStats();

    @Process(Policy.WRITE)
    Long getDB();

}
