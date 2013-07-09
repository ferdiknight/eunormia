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
import com.guang.eunormia.common.binary.RedisSortedSetCommands.Tuple;
import com.guang.eunormia.common.core.BaseCommands;
import com.guang.eunormia.common.core.SortedSetCommands;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * redis sorted set commands
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 5:04:52 PM
 * @version 0.0.1
 */
public class DefaultSortedSetCommands<K> extends BaseCommands implements SortedSetCommands<K>
{

    private RedisCommands redisCommands;

    public <K, V> DefaultSortedSetCommands()
    {
    }

    public <K, V> DefaultSortedSetCommands(RedisCommands redisCommands)
    {
        this.redisCommands = redisCommands;
    }

    public <K, V> RedisCommands getRedisCommands()
    {
        return redisCommands;
    }

    public <K, V> void setRedisCommands(RedisCommands redisCommands)
    {
        this.redisCommands = redisCommands;
    }

    public <K, V> void init()
    {
        if (commandsProvider == null)
        {
            throw new EunormiaException("commandsProvider is null.please set a commandsProvider first.");
        }
        this.redisCommands = commandsProvider.getEunormia();
    }

    @Override
    public <V> Boolean add(final int namespace, final K key, final V value, final double score)
    {
        return (Boolean) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.zAdd(rawKey(namespace, key), score, rawValue(value));
            }
        });
    }

    @Override
    public <V> Long add(final int namespace, final K key, final Map<V, Double> maps)
    {
        final Tuple[] tuples = new Tuple[maps.size()];
        int i = 0;
        for (Map.Entry<V, Double> m : maps.entrySet())
        {
            tuples[i++] = new Tuple(rawValue(m.getKey()), m.getValue());
        }
        return (Long) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.zAdd(rawKey(namespace, key), tuples);
            }
        });
    }

    @Override
    public Long count(final int namespace, final K key, final double min, final double max)
    {
        return (Long) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.zCount(rawKey(namespace, key), min, max);
            }
        });
    }

    @Override
    public <V> Double incrementScore(final int namespace, final K key, final V value, final double delta)
    {
        return (Double) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.zIncrBy(rawKey(namespace, key), delta, rawValue(value));
            }
        });
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
                return commands.zInterStore(rawKey(namespace, destKey), rawKeys(namespace, key, otherKeys));
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> Set<V> range(final int namespace, final K key, final long start, final long end)
    {
        return deserializeValues((Set<byte[]>) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.zRange(rawKey(namespace, key), start, end);
            }
        }));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> Map<V, Double> rangeWithScore(final int namespace, final K key, final long start, final long end)
    {
        return deserializeTruble((Set<Tuple>) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.zRangeWithScore(rawKey(namespace, key), start, end);
            }
        }));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> Set<V> rangeByScore(final int namespace, final K key, final double min, final double max)
    {
        return deserializeValues((Set<byte[]>) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.zRangeByScore(rawKey(namespace, key), min, max);
            }
        }));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> Set<V> rangeByScore(final int namespace, final K key, final double min, final double max, final int offset, final int count)
    {
        return deserializeValues((Set<byte[]>) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.zRangeByScore(rawKey(namespace, key), min, max, offset, count);
            }
        }));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> Map<V, Double> rangeByScoreWithScore(final int namespace, final K key, final double min, final double max)
    {
        return deserializeTruble((Set<Tuple>) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.zRangeByScoreWithScore(rawKey(namespace, key), min, max);
            }
        }));
    }

    @Override
    public Long rank(final int namespace, final K key, final Object o)
    {
        return (Long) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.zRank(rawKey(namespace, key), rawValue(o));
            }
        });
    }

    @Override
    public Long remove(final int namespace, final K key, final Object... o)
    {
        return (Long) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.zRem(rawKey(namespace, key), rawValues(o));
            }
        });
    }

    @Override
    public void removeRange(final int namespace, final K key, final long start, final long end)
    {
        doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.zRemRange(rawKey(namespace, key), start, end);
            }
        });
    }

    @Override
    public void removeRangeByScore(final int namespace, final K key, final double min, final double max)
    {
        doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.zRemRangeByScore(rawKey(namespace, key), min, max);
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> Set<V> reverseRange(final int namespace, final K key, final long start, final long end)
    {
        return deserializeValues((Set<byte[]>) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.zRevRange(rawKey(namespace, key), start, end);
            }
        }));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> Set<V> reverseRangeByScore(final int namespace, final K key, final double min, final double max)
    {
        return deserializeValues((Set<byte[]>) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.zRevRangeByScore(rawKey(namespace, key), min, max);
            }
        }));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> Map<V, Double> reverseRangeWithScore(final int namespace, final K key, final long start, final long end)
    {
        return deserializeTruble((Set<Tuple>) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.zRevRangeWithScore(rawKey(namespace, key), start, end);
            }
        }));
    }

    @Override
    public Long reverseRank(final int namespace, final K key, final Object o)
    {
        return (Long) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.zRevRank(rawKey(namespace, key), rawValue(o));
            }
        });
    }

    @Override
    public Double score(final int namespace, final K key, final Object o)
    {
        return (Double) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.zScore(rawKey(namespace, key), rawValue(o));
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
                return commands.zCard(rawKey(namespace, key));
            }
        });
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
                return commands.zUnionStore(rawKey(namespace, destKey), rawKeys(namespace, key, otherKeys));
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> Set<V> reverseRangeByScore(final int namespace, final K key, final double min, final double max, final int offset, final int count)
    {
        return deserializeValues((Set<byte[]>) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.zRevRangeByScore(rawKey(namespace, key), min, max, offset, count);
            }
        }));
    }
}
