/*
 * Copyright (C) 2013 ferdi
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 */

package com.guang.eunormia.common;

import com.guang.eunormia.common.binary.RedisCommands;
import com.guang.eunormia.common.config.HAConfig.ServerProperties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * a single node client for use
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jun 28, 2013 4:48:23 PM
 * @version 0.0.1
*/
public interface Node 
{
    /**
     * 获取单个Redis操作入口
     * @return
     */
    RedisCommands getEunormia();

    /**
     * 获取单个实例的配置
     * @return
     */
    ServerProperties getProperties();

    /**
     * 获取单个实例的失败次数
     * @return
     */
    AtomicInteger getErrorCount();
}
