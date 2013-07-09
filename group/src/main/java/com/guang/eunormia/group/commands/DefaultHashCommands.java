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
import com.guang.eunormia.common.core.HashCommands;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis hash commands
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 5:39:01 PM
 * @version 0.0.1
 */
public class DefaultHashCommands<K> extends BaseCommands implements HashCommands<K>
{

    protected RedisCommands redisCommands;

    public DefaultHashCommands()
    {
    }

    public DefaultHashCommands(RedisCommands redisCommands)
    {
        this.redisCommands = redisCommands;
    }

    public RedisCommands getRedisCommands()
    {
        return redisCommands;
    }

    public void setRedisCommands(RedisCommands redisCommands)
    {
        this.redisCommands = redisCommands;
    }

    public void init()
    {
        if (commandsProvider == null)
        {
            throw new EunormiaException("commandsProvider is null.please set a commandsProvider first.");
        }
        this.redisCommands = commandsProvider.getEunormia();
    }

    @Override
    public <HK> void delete(final int namespace, final K key, final HK... hashKey)
    {
        doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.hDel(rawKey(namespace, key), rawHashKeys(hashKey));
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public <HK, V> Map<HK, V> entries(final int namespace, final K key)
    {
        return deserializeHashMap((Map<byte[], byte[]>) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.hGetAll(rawKey(namespace, key));
            }
        }));
    }

    @Override
    public <HK, V> V get(final int namespace, final K key, final HK hashKey)
    {
        return deserializeHashValue((byte[]) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.hGet(rawKey(namespace, key), rawHashKey(hashKey));
            }
        }));
    }

    @Override
    public <HK> Boolean hasKey(final int namespace, final K key, final HK hashKey)
    {
        return (Boolean) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.hExists(rawKey(namespace, key), rawHashKey(hashKey));
            }
        });
    }

    @Override
    public <HK> Long increment(final int namespace, final K key, final HK hashKey, final long delta)
    {
        return (Long) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.hIncrBy(rawKey(namespace, key), rawHashKey(hashKey), delta);
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public <HK> Set<HK> keys(final int namespace, final K key)
    {
        return deserializeHashKeys((Set<byte[]>) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.hKeys(rawKey(namespace, key));
            }
        }));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <HK, V> Collection<V> multiGet(final int namespace, K key, Collection<HK> hashKeys)
    {
        if (hashKeys == null || hashKeys.isEmpty())
        {
            return Collections.emptyList();
        }

        final byte[] rawKey = rawKey(namespace, key);

        final byte[][] rawKashKeys = new byte[hashKeys.size()][];

        int counter = 0;
        for (HK hashKey : hashKeys)
        {
            rawKashKeys[counter++] = rawHashKey(hashKey);
        }
        return deserializeHashValues((List<byte[]>) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.hMGet(rawKey, rawKashKeys);
            }
        }));
    }

    @Override
    public <HK, V> void put(final int namespace, final K key, final HK hashKey, final V value)
    {
        doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.hSet(rawKey(namespace, key), rawHashKey(hashKey), rawHashValue(value));
            }
        });
    }

    @Override
    public <HK, V> void putAll(final int namespace, K key, Map<? extends HK, ? extends V> m)
    {
        if (m.isEmpty())
        {
            return;
        }

        final byte[] rawKey = rawKey(namespace, key);

        final Map<byte[], byte[]> hashes = new LinkedHashMap<byte[], byte[]>(m.size());

        for (Map.Entry<? extends HK, ? extends V> entry : m.entrySet())
        {
            hashes.put(rawHashKey(entry.getKey()), rawHashValue(entry.getValue()));
        }
        doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                commands.hMSet(rawKey, hashes);
                return null;
            }
        });
    }

    @Override
    public <HK, V> Boolean putIfAbsent(final int namespace, final K key, final HK hashKey, final V value)
    {
        return (Boolean) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.hSetNX(rawKey(namespace, key), rawHashKey(hashKey), rawHashValue(value));
            }
        });
    }

    @Override
    public Long size(final int namespace, final K key)
    {
        return (Long) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.hLen(rawKey(namespace, key));
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> Collection<V> values(final int namespace, final K key)
    {
        return deserializeHashValues((List<byte[]>) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.hVals(rawKey(namespace, key));
            }
        }));
    }
}
