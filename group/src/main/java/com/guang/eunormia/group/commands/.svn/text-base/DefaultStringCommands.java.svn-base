/*
 * Copyright (C) 2013 ferdi
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 */

package com.guang.eunormia.group.commands;

import com.guang.eunormia.common.EunormiaException;
import com.guang.eunormia.common.binary.RedisCommands;
import com.guang.eunormia.common.core.BaseCommands;
import com.guang.eunormia.common.core.StringCommands;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis String commands
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 5:23:53 PM
 * @version 0.0.1
*/
public class DefaultStringCommands extends BaseCommands implements StringCommands
{
    private RedisCommands redisCommands;

    public DefaultStringCommands() {
        setKeySerializer(stringSerializer);
        setValueSerializer(stringSerializer);
    }

    public DefaultStringCommands(RedisCommands redisCommands) {
        this.redisCommands = redisCommands;
        setKeySerializer(stringSerializer);
        setValueSerializer(stringSerializer);
    }

    public RedisCommands getRedisCommands() {
        return redisCommands;
    }

    public void setRedisCommands(RedisCommands redisCommands) {
        this.redisCommands = redisCommands;
    }

    public void init() {
        if (commandsProvider == null) {
            throw new EunormiaException("commandsProvider is null.please set a commandsProvider first.");
        }
        this.redisCommands = commandsProvider.getEunormia();
    }

    @Override
    public Long append(final int namespace, final String key, final String value) {
        return (Long) doInEunormia(namespace, new EunormiaBlock(redisCommands) {
            @Override
            public Object execute() {
                return commands.append(rawKey(namespace, key), rawString(value));
            }
        });
    }

    @Override
    public String get(final int namespace, final Object key) {
        return deserializeString((byte[]) doInEunormia(namespace, new EunormiaBlock(redisCommands) {
            @Override
            public Object execute() {
                return commands.get(rawKey(namespace, key));
            }
        }));
    }

    @Override
    public String get(final int namespace, final String key, final long start, final long end) {
        return deserializeString((byte[]) doInEunormia(namespace, new EunormiaBlock(redisCommands) {
            @Override
            public Object execute() {
                return commands.getRange(rawKey(namespace, key), (int) start, (int) end);
            }
        }));
    }

    @Override
    public String getAndSet(final int namespace, final String key, final String value) {
        return deserializeString((byte[]) doInEunormia(namespace, new EunormiaBlock(redisCommands) {
            @Override
            public Object execute() {
                return commands.getSet(rawKey(namespace, key), rawString(value));
            }
        }));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> multiGet(final int namespace, final Collection<String> keys) {
        if (keys.isEmpty()) {
            return Collections.emptyList();
        }
        final byte[][] rawKeys = new byte[keys.size()][];
        int counter = 0;
        for (String hashKey : keys) {
            rawKeys[counter++] = rawKey(namespace, hashKey);
        }
        return deserializeStrings((List<byte[]>) doInEunormia(namespace, new EunormiaBlock(redisCommands) {
            @Override
            public Object execute() {
                return commands.mGet(rawKeys);
            }
        }));
    }

    @Override
    public void multiSet(final int namespace, final Map<String, String> m) {
        if (m.isEmpty()) {
            return;
        }
        final Map<byte[], byte[]> rawKeys = new LinkedHashMap<byte[], byte[]>(m.size());
        for (Map.Entry<String, String> entry : m.entrySet()) {
            rawKeys.put(rawKey(namespace, entry.getKey()), rawString(entry.getValue()));
        }
        doInEunormia(namespace, new EunormiaBlock(redisCommands) {
            @Override
            public Object execute() {
                commands.mSet(rawKeys);
                return null;
            }
        });
    }

    @Override
    public void multiSetIfAbsent(final int namespace, final Map<String, String> m) {
        if (m.isEmpty()) {
            return;
        }

        final Map<byte[], byte[]> rawKeys = new LinkedHashMap<byte[], byte[]>(m.size());

        for (Map.Entry<String, String> entry : m.entrySet()) {
            rawKeys.put(rawKey(namespace, entry.getKey()), rawString(entry.getValue()));
        }
        doInEunormia(namespace, new EunormiaBlock(redisCommands) {
            @Override
            public Object execute() {
                commands.mSetNX(rawKeys);
                return null;
            }
        });

    }

    @Override
    public void set(final int namespace, final String key, final String value) {
        doInEunormia(namespace, new EunormiaBlock(redisCommands) {
            @Override
            public Object execute() {
                commands.set(rawKey(namespace, key), rawString(value));
                return null;
            }
        });
    }

    @Override
    public void set(final int namespace, final String key, final String value, final long timeout, final TimeUnit unit) {
        doInEunormia(namespace, new EunormiaBlock(redisCommands) {
            @Override
            public Object execute() {
                commands.setEx(rawKey(namespace, key), (int) unit.toSeconds(timeout), rawString(value));
                return null;
            }
        });
    }

    @Override
    public void set(final int namespace, final String key, final String value, final long offset) {
        doInEunormia(namespace, new EunormiaBlock(redisCommands) {
            @Override
            public Object execute() {
                commands.setRange(rawKey(namespace, key), rawString(value), offset);
                return null;
            }
        });
    }

    @Override
    public Boolean setIfAbsent(final int namespace, final String key, final String value) {
        return (Boolean) doInEunormia(namespace, new EunormiaBlock(redisCommands) {
            @Override
            public Object execute() {
                return commands.setNX(rawKey(namespace, key), rawString(value));
            }
        });
    }

    @Override
    public Long size(final int namespace, final String key) {
        return (Long) doInEunormia(namespace, new EunormiaBlock(redisCommands) {
            @Override
            public Object execute() {
                return commands.strLen(rawKey(namespace, key));
            }
        });
    }
}
