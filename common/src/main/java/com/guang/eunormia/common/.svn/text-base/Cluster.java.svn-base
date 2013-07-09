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
import com.guang.eunormia.common.config.ConfigManager;

/**
 * cluster interface for use
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jun 28, 2013 4:55:46 PM
 * @version 0.0.1
*/
public interface Cluster 
{
    /**
     * 获取集群操作入口
     * @return
     */
    RedisCommands getEunormia();

    /**
     * 注销集群实例
     */
    void destroy();

    /**
     * 设置配置管理器，默认实现有：Diamond，ZK和文件
     * @param cm
     */
    void setConfigManager(ConfigManager cm);

    /**
     * 使用集群之前需要先初始化
     */
    void init();
}
