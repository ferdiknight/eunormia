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

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 11:02:25 AM
 * @version 0.0.1
*/
public interface RedisTransactionCommands 
{
    @Process(Policy.WRITE)
    Boolean multi();

    @Process(Policy.WRITE)
    List<Object> exec();

    @Process(Policy.WRITE)
    Boolean discard();

    @Process(Policy.WRITE)
    Boolean watch(byte[]... keys);

    @Process(Policy.WRITE)
    Boolean unwatch();

}
