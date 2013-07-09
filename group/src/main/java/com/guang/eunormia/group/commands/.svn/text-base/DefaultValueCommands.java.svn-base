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
import com.guang.eunormia.common.cache.DefaultLocalCache;
import com.guang.eunormia.common.cache.LocalCache;
import com.guang.eunormia.common.core.BaseCommands;
import com.guang.eunormia.common.core.ValueCommands;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *  redis key/value commands
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 5:13:19 PM
 * @version 0.0.1
 */
public class DefaultValueCommands<K> extends BaseCommands implements ValueCommands<K>
{

    protected RedisCommands redisCommands;
    @SuppressWarnings("rawtypes")
    protected LocalCache localCache;

    @SuppressWarnings("rawtypes")
    public DefaultValueCommands()
    {
        localCache = new DefaultLocalCache();
    }

    @SuppressWarnings("rawtypes")
    public DefaultValueCommands(RedisCommands redisCommands)
    {
        this.redisCommands = redisCommands;
        localCache = new DefaultLocalCache();
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
    public <V> V get(final int namespace, final K key)
    {
        return deserializeValue((byte[]) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.get(rawKey(namespace, key));
            }
        }));
    }

    @Override
    public <V> V getAndSet(final int namespace, final K key, final V value)
    {
        return deserializeValue((byte[]) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.getSet(rawKey(namespace, key), rawValue(value));
            }
        }));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> List<V> multiGet(final int namespace, final Collection<K> keys)
    {
        if (keys.isEmpty())
        {
            return Collections.emptyList();
        }
        final byte[][] rawKeys = new byte[keys.size()][];
        int counter = 0;
        for (K hashKey : keys)
        {
            rawKeys[counter++] = rawKey(namespace, hashKey);
        }
        return deserializeValues((List<byte[]>) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.mGet(rawKeys);
            }
        }));
    }

    @Override
    public <V> void multiSet(final int namespace, final Map<? extends K, ? extends V> m)
    {
        if (m.isEmpty())
        {
            return;
        }
        final Map<byte[], byte[]> rawKeys = new LinkedHashMap<byte[], byte[]>(m.size());
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet())
        {
            rawKeys.put(rawKey(namespace, entry.getKey()), rawValue(entry.getValue()));
        }
        doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                commands.mSet(rawKeys);
                return null;
            }
        });
    }

    @Override
    public <V> void multiSetIfAbsent(final int namespace, final Map<? extends K, ? extends V> m)
    {
        if (m.isEmpty())
        {
            return;
        }

        final Map<byte[], byte[]> rawKeys = new LinkedHashMap<byte[], byte[]>(m.size());

        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet())
        {
            rawKeys.put(rawKey(namespace, entry.getKey()), rawValue(entry.getValue()));
        }
        doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                commands.mSetNX(rawKeys);
                return null;
            }
        });

    }

    @Override
    public <V> void set(final int namespace, final K key, final V value)
    {
        doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                commands.set(rawKey(namespace, key), rawValue(value));
                return null;
            }
        });
    }

    @Override
    public <V> void set(final int namespace, final K key, final V value, final long timeout, final TimeUnit unit)
    {
        doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                commands.setEx(rawKey(namespace, key), (int) unit.toSeconds(timeout), rawValue(value));
                return null;
            }
        });
    }

    @Override
    public <V> Boolean setIfAbsent(final int namespace, final K key, final V value)
    {
        return (Boolean) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.setNX(rawKey(namespace, key), rawValue(value));
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> V get(int namespace, K key, long timeout, TimeUnit unit)
    {
        V result = (V) localCache.get(key);
        if (result == null)
        {
            result = get(namespace, key);
            if (result != null)
            {
                localCache.put(key, result, (int) unit.toSeconds(timeout));
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> V get(int namespace, K key, Date expireAt)
    {
        V result = (V) localCache.get(key);
        if (result == null)
        {
            result = get(namespace, key);
            if (result != null)
            {
                localCache.put(key, result, expireAt);
            }
        }
        return result;
    }

    @Override
    public <V> List<V> multiGet(int namespace, Collection<K> keys, long timeout, TimeUnit unit)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public <V> List<V> multiGet(int namespace, Collection<K> keys, Date expireAt)
    {
        throw new UnsupportedOperationException();
    }
}
