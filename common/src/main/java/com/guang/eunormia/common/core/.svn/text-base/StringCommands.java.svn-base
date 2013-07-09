/**
 * (C) 2011-2012 Alibaba Group Holding Limited.
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License version 2 as published by the
 * Free Software Foundation.
 *
 */
package com.guang.eunormia.common.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface StringCommands
{

    Long append(int namespace, String key, String value);

    String get(int namespace, String key, long start, long end);

    void set(int namespace, String key, String value, long offset);

    Long size(int namespace, String key);

    void set(int namespace, String key, String value);

    void set(int namespace, String key, String value, long timeout, TimeUnit unit);

    Boolean setIfAbsent(int namespace, String key, String value);

    void multiSet(int namespace, Map<String, String> m);

    void multiSetIfAbsent(int namespace, Map<String, String> m);

    String get(int namespace, Object key);

    String getAndSet(int namespace, String key, String value);

    List<String> multiGet(int namespace, Collection<String> keys);
}