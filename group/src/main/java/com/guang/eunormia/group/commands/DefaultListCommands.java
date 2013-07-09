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
import com.guang.eunormia.common.binary.RedisListCommands.Position;
import com.guang.eunormia.common.core.BaseCommands;
import com.guang.eunormia.common.core.ListCommands;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis list commands
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 5:27:51 PM
 * @version 0.0.1
 */
public class DefaultListCommands<K> extends BaseCommands implements ListCommands<K>
{

    protected RedisCommands redisCommands;

    public DefaultListCommands()
    {
    }

    public DefaultListCommands(RedisCommands redisCommands)
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
    public <V> V index(final int namespace, final K key, final long index)
    {
        return deserializeValue((byte[]) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.lIndex(rawKey(namespace, key), index);
            }
        }));
    }

    @Override
    public <V> V leftPop(final int namespace, final K key)
    {
        return deserializeValue((byte[]) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.lPop(rawKey(namespace, key));
            }
        }));
    }

    @Override
    public <V> V leftPop(final int namespace, final K key, final long timeout, final TimeUnit unit)
    {
        final int tm = (int) unit.toSeconds(timeout);
        return deserializeValue((byte[]) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.bLPop(tm, rawKey(namespace, key));
            }
        }));
    }

    @Override
    public <V> Long leftPush(final int namespace, final K key, final V... value)
    {
        return (Long) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.lPush(rawKey(namespace, key), rawValues(value));
            }
        });
    }

    @Override
    public <V> Long leftInsert(final int namespace, final K key, final V pivot, final V value)
    {
        return (Long) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.lInsert(rawKey(namespace, key), Position.BEFORE, rawValue(pivot), rawValue(value));
            }
        });
    }

    @Override
    public <V> Long leftPushIfPresent(final int namespace, final K key, final V value)
    {
        return (Long) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.lPushX(rawKey(namespace, key), rawValue(value));
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> List<V> range(final int namespace, final K key, final long start, final long end)
    {
        return deserializeValues((List<byte[]>) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.lRange(rawKey(namespace, key), start, end);
            }
        }));
    }

    @Override
    public Long remove(final int namespace, final K key, final long i, final Object value)
    {
        return (Long) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.lRem(rawKey(namespace, key), i, rawValue(value));
            }
        });
    }

    @Override
    public <V> V rightPop(final int namespace, final K key)
    {
        return deserializeValue((byte[]) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.rPop(rawKey(namespace, key));
            }
        }));
    }

    @Override
    public <V> V rightPop(final int namespace, final K key, final long timeout, final TimeUnit unit)
    {
        final int tm = (int) unit.toSeconds(timeout);
        return deserializeValue((byte[]) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.bRPop(tm, rawKey(namespace, key));
            }
        }));
    }

    @Override
    public <V> V rightPopAndLeftPush(final int namespace, final K sourceKey, final K destinationKey)
    {
        return deserializeValue((byte[]) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.rPopLPush(rawKey(namespace, sourceKey), rawKey(namespace, destinationKey));
            }
        }));
    }

    @Override
    public <V> V rightPopAndLeftPush(final int namespace, final K sourceKey, final K destinationKey, long timeout, TimeUnit unit)
    {
        final int tm = (int) unit.toSeconds(timeout);
        return deserializeValue((byte[]) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.bRPopLPush(tm, rawKey(namespace, sourceKey), rawKey(namespace, destinationKey));
            }
        }));
    }

    @Override
    public <V> Long rightPush(final int namespace, final K key, final V... value)
    {
        return (Long) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.rPush(rawKey(namespace, key), rawValues(value));
            }
        });
    }

    @Override
    public <V> Long rightInsert(final int namespace, final K key, final V pivot, final V value)
    {
        return (Long) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.lInsert(rawKey(namespace, key), Position.AFTER, rawValue(pivot), rawValue(value));
            }
        });
    }

    @Override
    public <V> Long rightPushIfPresent(final int namespace, final K key, final V value)
    {
        return (Long) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.rPushX(rawKey(namespace, key), rawValue(value));
            }
        });
    }

    @Override
    public <V> void set(final int namespace, final K key, final long index, final V value)
    {
        doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                commands.lSet(rawKey(namespace, key), index, rawValue(value));
                return null;
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
                return commands.lLen(rawKey(namespace, key));
            }
        });
    }

    @Override
    public void trim(final int namespace, final K key, final long start, final long end)
    {
        doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                commands.lTrim(rawKey(namespace, key), start, end);
                return null;
            }
        });
    }
}
