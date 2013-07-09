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
import com.guang.eunormia.common.core.SetCommands;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * redis set commands
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 5:25:17 PM
 * @version 0.0.1
 */
public class DefaultSetCommands<K> extends BaseCommands implements SetCommands<K>
{

    private RedisCommands redisCommands;

    public DefaultSetCommands()
    {
    }

    public DefaultSetCommands(RedisCommands redisCommands)
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
    public <V> Long add(final int namespace, final K key, final V... value)
    {
        return (Long) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.sAdd(rawKey(namespace, key), rawValues(value));
            }
        });
    }

    @Override
    public <V> Set<V> difference(final int namespace, K key, K otherKey)
    {
        return difference(namespace, key, Collections.singleton(otherKey));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> Set<V> difference(final int namespace, final K key, final Collection<K> otherKeys)
    {
        return deserializeValues((Set<byte[]>) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.sDiff(rawKeys(namespace, key, otherKeys));
            }
        }));
    }

    @Override
    public void differenceAndStore(final int namespace, K key, K otherKey, K destKey)
    {
        differenceAndStore(namespace, key, Collections.singleton(otherKey), destKey);
    }

    @Override
    public void differenceAndStore(final int namespace, final K key, final Collection<K> otherKeys, final K destKey)
    {
        doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                commands.sDiffStore(rawKey(namespace, destKey), rawKeys(namespace, key, otherKeys));
                return null;
            }
        });
    }

    @Override
    public <V> Set<V> intersect(final int namespace, final K key, final K otherKey)
    {
        return intersect(namespace, key, Collections.singleton(otherKey));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> Set<V> intersect(final int namespace, final K key, final Collection<K> otherKeys)
    {
        return deserializeValues((Set<byte[]>) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.sInter(rawKeys(namespace, key, otherKeys));
            }
        }));
    }

    @Override
    public void intersectAndStore(final int namespace, K key, K otherKey, K destKey)
    {
        intersectAndStore(namespace, key, Collections.singleton(otherKey), destKey);
    }

    @Override
    public void intersectAndStore(final int namespace, final K key, final Collection<K> otherKeys, final K destKey)
    {
        doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                commands.sInterStore(rawKey(namespace, destKey), rawKeys(namespace, key, otherKeys));
                return null;
            }
        });
    }

    @Override
    public Boolean isMember(final int namespace, final K key, final Object o)
    {
        return (Boolean) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.sIsMember(rawKey(namespace, key), rawValue(o));
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> Set<V> members(final int namespace, final K key)
    {
        return deserializeValues((Set<byte[]>) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.sMembers(rawKey(namespace, key));
            }
        }));
    }

    @Override
    public <V> Boolean move(final int namespace, final K key, final V value, final K destKey)
    {
        return (Boolean) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.sMove(rawKey(namespace, key), rawKey(namespace, destKey), rawValue(value));
            }
        });
    }

    @Override
    public <V> V pop(final int namespace, final K key)
    {
        return deserializeValue((byte[]) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.sPop(rawKey(namespace, key));
            }
        }));
    }

    @Override
    public <V> V randomMember(final int namespace, final K key)
    {
        return deserializeValue((byte[]) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.sRandMember(rawKey(namespace, key));
            }
        }));
    }

    @Override
    public Long remove(final int namespace, final K key, final Object... o)
    {
        return (Long) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.sRem(rawKey(namespace, key), rawValues(o));
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
                return commands.sCard(rawKey(namespace, key));
            }
        });
    }

    @Override
    public <V> Set<V> union(final int namespace, final K key, final K otherKey)
    {
        return union(namespace, key, Collections.singleton(otherKey));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> Set<V> union(final int namespace, final K key, final Collection<K> otherKeys)
    {
        return deserializeValues((Set<byte[]>) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.sUnion(rawKeys(namespace, key, otherKeys));
            }
        }));
    }

    @Override
    public void unionAndStore(final int namespace, K key, K otherKey, K destKey)
    {
        unionAndStore(namespace, key, Collections.singleton(otherKey), destKey);
    }

    @Override
    public void unionAndStore(final int namespace, final K key, final Collection<K> otherKeys, final K destKey)
    {
        doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                commands.sUnionStore(rawKey(namespace, destKey), rawKeys(namespace, key, otherKeys));
                return null;
            }
        });
    }
}
