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
import com.guang.eunormia.common.core.AtomicCommands;
import com.guang.eunormia.common.core.BaseCommands;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 原子计数操作类
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 4:54:59 PM
 * @version 0.0.1
*/
public class DefaultAtomicCommands<K> extends BaseCommands implements AtomicCommands<K>
{
    protected RedisCommands redisCommands;

    public DefaultAtomicCommands() {
    }

    public DefaultAtomicCommands(RedisCommands redisCommands) {
        this.redisCommands = redisCommands;
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
    public long get(final int namespace, final K key) {
        return deserializeLong((byte[]) doInEunormia(namespace, new EunormiaBlock(redisCommands) {
            @Override
            public Object execute() {
                return commands.get(rawKey(namespace, key));
            }
        }));
    }


    @Override
    public long getAndSet(final int namespace, final K key, final long value) {
        return deserializeLong((byte[]) doInEunormia(namespace, new EunormiaBlock(redisCommands) {
            @Override
            public Object execute() {
                return commands.getSet(rawKey(namespace, key), rawLong(value));
            }
        }));
    }

    @Override
    public Long increment(final int namespace, final K key, final long delta) {
        return (Long)doInEunormia(namespace, new EunormiaBlock(redisCommands) {
            @Override
            public Object execute() {
                byte[] rawKey = rawKey(namespace, key);
                if (delta == 1) {
                    return commands.incr(rawKey);
                }
                if (delta == -1) {
                    return commands.decr(rawKey);
                }
                if (delta < 0) {
                    return commands.decrBy(rawKey, delta);
                }
                return commands.incrBy(rawKey, delta);
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> multiGet(final int namespace, final Collection<? extends K> keys) {
        if (keys.isEmpty()) {
            return Collections.emptyList();
        }
        final byte[][] rawKeys = new byte[keys.size()][];
        int counter = 0;
        for (K hashKey : keys) {
            rawKeys[counter++] = rawKey(namespace, hashKey);
        }
        return deserializeLongs((List<byte[]>) doInEunormia(namespace, new EunormiaBlock(redisCommands) {
            @Override
            public Object execute() {
                return commands.mGet(rawKeys);
            }
        }));
    }

    @Override
    public void multiSet(final int namespace, final Map<? extends K, Long> m) {
        if (m.isEmpty()) {
            return;
        }
        final Map<byte[], byte[]> rawKeys = new LinkedHashMap<byte[], byte[]>(m.size());
        for (Map.Entry<? extends K, Long> entry : m.entrySet()) {
            rawKeys.put(rawKey(namespace, entry.getKey()), rawLong(entry.getValue()));
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
    public void multiSetIfAbsent(final int namespace, final Map<? extends K, Long> m) {
        if (m.isEmpty()) {
            return;
        }

        final Map<byte[], byte[]> rawKeys = new LinkedHashMap<byte[], byte[]>(m.size());

        for (Map.Entry<? extends K, Long> entry : m.entrySet()) {
            rawKeys.put(rawKey(namespace, entry.getKey()), rawLong(entry.getValue()));
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
    public void set(final int namespace, final K key, final long value) {
        doInEunormia(namespace, new EunormiaBlock(redisCommands) {
            @Override
            public Object execute() {
                commands.set(rawKey(namespace, key), rawLong(value));
                return null;
            }
        });
    }

    @Override
    public void set(final int namespace, final K key, final long value, final long timeout, final TimeUnit unit) {
        doInEunormia(namespace, new EunormiaBlock(redisCommands) {
            @Override
            public Object execute() {
                commands.setEx(rawKey(namespace, key), (int) unit.toSeconds(timeout), rawLong(value));
                return null;
            }
        });
    }

    @Override
    public Boolean setIfAbsent(final int namespace, final K key, final long value) {
        return (Boolean)doInEunormia(namespace, new EunormiaBlock(redisCommands) {
            @Override
            public Object execute() {
                return commands.setNX(rawKey(namespace, key), rawLong(value));
            }
        });
    }
}
