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

import com.guang.eunormia.common.Cluster;
import com.guang.eunormia.common.EunormiaException;
import com.guang.eunormia.common.binary.DataType;
import com.guang.eunormia.common.binary.RedisCommands;
import com.guang.eunormia.common.core.AtomicCommands;
import com.guang.eunormia.common.core.BaseCommands;
import com.guang.eunormia.common.core.EunormiaManager;
import com.guang.eunormia.common.core.HashCommands;
import com.guang.eunormia.common.core.ListCommands;
import com.guang.eunormia.common.core.SetCommands;
import com.guang.eunormia.common.core.SortedSetCommands;
import com.guang.eunormia.common.core.StringCommands;
import com.guang.eunormia.common.core.ValueCommands;
import com.guang.eunormia.common.serializer.SerializationUtils;
import com.guang.eunormia.common.util.SortParams;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Eunormia Node 管理器的默认实现
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 4:50:43 PM
 * @version 0.0.1
 */
public class DefaultEunormiaManager<K> extends BaseCommands implements EunormiaManager<K>
{

    private RedisCommands redisCommands;
    private DefaultAtomicCommands atomicCommands;
    private DefaultStringCommands stringCommands;
    private DefaultValueCommands valueCommands;
    private DefaultListCommands listCommands;
    private DefaultHashCommands hashCommands;
    private DefaultSetCommands setCommands;
    private DefaultSortedSetCommands sortedSetCommands;

    public DefaultEunormiaManager()
    {
    }

    public DefaultEunormiaManager(Cluster provider)
    {
        this.commandsProvider = provider;
        init();
    }

    public void setRedisCommands(Cluster provider)
    {
        this.commandsProvider = provider;
        init();
    }

    private void init()
    {
        if (commandsProvider == null)
        {
            throw new EunormiaException("commandsProvider is null.please set a commandsProvider first.");
        }
        this.redisCommands = commandsProvider.getEunormia();
    }

    @Override
    public void delete(final int namespace, final K key)
    {
        delete(namespace, Collections.singleton(key));
    }

    @Override
    public void delete(final int namespace, final Collection<K> keys)
    {
        doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                commands.del(rawKeys(namespace, keys));
                return null;
            }
        });
    }

    @Override
    public Boolean expire(final int namespace, final K key, long timeout, TimeUnit unit)
    {
        final long seconds = unit.toSeconds(timeout);
        return (Boolean) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.expire(rawKey(namespace, key), seconds);
            }
        });
    }

    @Override
    public Boolean expireAt(final int namespace, final K key, final Date date)
    {
        return (Boolean) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.expireAt(rawKey(namespace, key), date.getTime());
            }
        });
    }

    @Override
    public AtomicCommands getAtomicCommands()
    {
        if (atomicCommands == null)
        {
            atomicCommands = new DefaultAtomicCommands(this.redisCommands);
            initSerializer(atomicCommands);
        }
        return atomicCommands;
    }

    @Override
    public Long getExpire(final int namespace, final K key)
    {
        return (Long) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.ttl(rawKey(namespace, key));
            }
        });
    }

    @Override
    public HashCommands getHashCommands()
    {
        if (hashCommands == null)
        {
            hashCommands = new DefaultHashCommands(this.redisCommands);
            initSerializer(hashCommands);
        }
        return hashCommands;
    }

    @Override
    public ListCommands getListCommands()
    {
        if (listCommands == null)
        {
            listCommands = new DefaultListCommands(this.redisCommands);
            initSerializer(listCommands);
        }
        return listCommands;
    }

    @Override
    public SetCommands getSetCommands()
    {
        if (setCommands == null)
        {
            setCommands = new DefaultSetCommands(this.redisCommands);
            initSerializer(setCommands);
        }
        return setCommands;
    }

    @Override
    public StringCommands getStringCommands()
    {
        if (stringCommands == null)
        {
            stringCommands = new DefaultStringCommands(this.redisCommands);
            initSerializer(stringCommands);
        }
        return stringCommands;
    }

    @Override
    public ValueCommands getValueCommands()
    {
        if (valueCommands == null)
        {
            valueCommands = new DefaultValueCommands(this.redisCommands);
            initSerializer(valueCommands);
        }
        return valueCommands;
    }

    @Override
    public SortedSetCommands getZSetCommands()
    {
        if (sortedSetCommands == null)
        {
            sortedSetCommands = new DefaultSortedSetCommands(this.redisCommands);
            initSerializer(sortedSetCommands);
        }
        return sortedSetCommands;
    }

    private void initSerializer(BaseCommands baseCommands)
    {
        baseCommands.setKeySerializer(getKeySerializer());
        baseCommands.setValueSerializer(getValueSerializer());
        baseCommands.setStringSerializer(getStringSerializer());
        baseCommands.setHashKeySerializer(getHashKeySerializer());
        baseCommands.setHashValueSerializer(getHashValueSerializer());
    }

    @Override
    public Boolean hasKey(final int namespace, final K key)
    {
        return (Boolean) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.exists(rawKey(namespace, key));
            }
        });
    }

    @Override
    public Set<K> keys(final int namespace, final String pattern)
    {
        Set<byte[]> bytekeys = (Set<byte[]>) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.keys((namespace + ":" + pattern).getBytes());
            }
        });
        Set<byte[]> newbytekeys = new HashSet<byte[]>();
        for (byte[] bytekey : bytekeys)
        {
            newbytekeys.add(removeNamespaceFromKey(bytekey));
        }
        return SerializationUtils.deserialize(newbytekeys, getKeySerializer());
    }

    @Override
    public Boolean persist(final int namespace, final K key)
    {
        return (Boolean) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.persist(rawKey(namespace, key));
            }
        });
    }

    @Override
    public void rename(final int namespace, final K oldKey, final K newKey)
    {
        doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                commands.rename(rawKey(namespace, oldKey), rawKey(namespace, newKey));
                return null;
            }
        });
    }

    @Override
    public Boolean renameIfAbsent(final int namespace, final K oldKey, final K newKey)
    {
        return (Boolean) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.renameNX(rawKey(namespace, oldKey), rawKey(namespace, newKey));
            }
        });
    }

    @Override
    public <V> List<V> sort(final int namespace, final K key, final SortParams params)
    {
        return deserializeValues((List<byte[]>) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.sort(rawKey(namespace, key), params);
            }
        }));
    }

    @Override
    public Long sort(final int namespace, final K key, final SortParams params, final K storeKey)
    {
        return (Long) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.sort(rawKey(namespace, key), params, rawKey(namespace, storeKey));
            }
        });
    }

    @Override
    public DataType type(final int namespace, final K key)
    {
        return DataType.fromCode((String) doInEunormia(namespace, new EunormiaBlock(redisCommands)
        {
            @Override
            public Object execute()
            {
                return commands.type(rawKey(namespace, key));
            }
        }));
    }

    public Cluster getProvider()
    {
        return this.commandsProvider;
    }

    @Override
    public void destroy()
    {
        if (this.commandsProvider != null)
        {
            this.commandsProvider.destroy();
        }
    }
}
