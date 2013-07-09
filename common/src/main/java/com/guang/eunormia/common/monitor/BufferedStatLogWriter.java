/*
 * Copyright (C) 2013 ferdi
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 */
package com.guang.eunormia.common.monitor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 4:29:07 PM
 * @version 0.0.1
 */
public class BufferedStatLogWriter
{

    public static final Logger statlog = LoggerInit.EUNORMIA_LOG;
    public static final String logFieldSep = "#@#";
    public static final String typeCount = "count";
    public static final String typeTime = "time";
    public static final String linesep = System.getProperty("line.separator");
    public static volatile int maxkeysize = 1024;
    public static volatile int dumpInterval = 300;
    public static final SimpleDateFormat df = new SimpleDateFormat("yyy-MM-dd HH:mm:ss:SSS");
    private static LogWriter logWriter = new LogWriter()
    {
        private void addLine(StringBuilder sb, Object key, Object group, Object flag, StatCounter sc, String time)
        {
            sb.append(key).append(logFieldSep).append(group).append(logFieldSep).append(flag).append(logFieldSep).append(typeCount).append(logFieldSep).append(sc.getCount()).append(logFieldSep)
                    .append(sc.getValue()).append(logFieldSep).append(time).append(linesep);
            sb.append(key).append(logFieldSep).append(group).append(logFieldSep).append(flag).append(logFieldSep).append(typeTime).append(logFieldSep).append(sc.getMin()).append(logFieldSep).append(
                    sc.getMax()).append(logFieldSep).append(time).append(linesep);
        }

        @Override
        public void writeLog(Map<Object, ConcurrentHashMap<Object, ConcurrentHashMap<Object, StatCounter>>> map)
        {
            statlog.debug(Thread.currentThread().getName() + "[writeLog]map.size()=" + map.size() + linesep);
            StringBuilder sb = new StringBuilder();
            String time = df.format(new Date());
            for (Map.Entry<Object, ConcurrentHashMap<Object, ConcurrentHashMap<Object, StatCounter>>> e0 : map.entrySet())
            {
                for (Map.Entry<Object, ConcurrentHashMap<Object, StatCounter>> e1 : e0.getValue().entrySet())
                {
                    for (Map.Entry<Object, StatCounter> e2 : e1.getValue().entrySet())
                    {
                        StatCounter sc = e2.getValue();
                        addLine(sb, e0.getKey(), e1.getKey(), e2.getKey(), sc, time);
                    }
                }
            }
            statlog.warn(sb);
        }
    };

    public static void setLogWriter(LogWriter logWriter)
    {
        BufferedStatLogWriter.logWriter = logWriter;
    }

    public static interface LogWriter
    {
        void writeLog(Map<Object, ConcurrentHashMap<Object, ConcurrentHashMap<Object, StatCounter>>> map);
    }

    public static class StatCounter
    {

        private final AtomicLong count = new AtomicLong(0L);
        private final AtomicLong value = new AtomicLong(0L);
        private final AtomicLong min = new AtomicLong(Long.MAX_VALUE);
        private final AtomicLong max = new AtomicLong(Long.MIN_VALUE);

        public void add(long c, long v)
        {
            this.count.addAndGet(c);
            this.value.addAndGet(v);
            while (true)
            {
                long vmin = min.get();
                if (v < vmin)
                {
                    if (min.compareAndSet(vmin, v))
                    {
                        break;
                    }
                    continue;
                }
                break;
            }
            while (true)
            {
                long vmax = max.get();
                if (v > vmax)
                {
                    if (max.compareAndSet(vmax, v))
                    {
                        break;
                    }
                    continue;
                }
                break;
            }
        }

        public synchronized void reset()
        {
            this.count.set(0L);
            this.value.set(0L);
            this.min.set(Long.MAX_VALUE);
            this.max.set(Long.MIN_VALUE);
        }

        public long getCount()
        {
            return this.count.get();
        }

        public long getValue()
        {
            return this.value.get();
        }

        public long getMin()
        {
            return this.min.get();
        }

        public long getMax()
        {
            return this.max.get();
        }

        public long[] get()
        {
            return new long[]
            {
                this.count.get(), this.value.get(), this.min.get(), this.max.get()
            };
        }
    }
    private static ConcurrentHashMap<Object, ConcurrentHashMap<Object, ConcurrentHashMap<Object, StatCounter>>> keys = new ConcurrentHashMap<Object, ConcurrentHashMap<Object, ConcurrentHashMap<Object, StatCounter>>>(
            1024, 0.75f, 32);

    public static void add(Object key, Object group, Object flag, long count, long timeuse)
    {
        ConcurrentHashMap<Object, ConcurrentHashMap<Object, ConcurrentHashMap<Object, StatCounter>>> oldkeys = keys;
        ConcurrentHashMap<Object, ConcurrentHashMap<Object, StatCounter>> groups = oldkeys.get(key);
        if (groups == null)
        {
            ConcurrentHashMap<Object, ConcurrentHashMap<Object, StatCounter>> newGroups = new ConcurrentHashMap<Object, ConcurrentHashMap<Object, StatCounter>>();
            groups = oldkeys.putIfAbsent(key, newGroups);
            if (groups == null)
            {
                groups = newGroups;
                insureSize();
            }
        }
        ConcurrentHashMap<Object, StatCounter> flags = groups.get(group);
        if (flags == null)
        {
            ConcurrentHashMap<Object, StatCounter> newFlags = new ConcurrentHashMap<Object, StatCounter>();
            flags = groups.putIfAbsent(group, newFlags);
            if (flags == null)
            {
                flags = newFlags;
            }
        }
        StatCounter counter = flags.get(flag);
        if (counter == null)
        {
            StatCounter newCounter = new StatCounter();
            counter = flags.putIfAbsent(flag, newCounter);
            if (counter == null)
            {
                counter = newCounter;
            }
        }
        counter.add(count, timeuse);
    }
    private static Lock lock = new ReentrantLock();
    private static volatile boolean isInFlushing = false;
    private static ExecutorService flushExecutor = Executors.newSingleThreadExecutor();
    private static final Thread fullDumpThread;

    static
    {
        LoggerInit.initEunormiaLog();
        fullDumpThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    try
                    {
                        Thread.sleep(dumpInterval * 1000);
                    }
                    catch (InterruptedException e)
                    {
                    }
                    submitFlush(true);
                }
            }
        });
        fullDumpThread.start();
    }

    private static void insureSize()
    {
        if (keys.size() < maxkeysize)
        {
            return;
        }
        submitFlush(false);
    }

    private static boolean submitFlush(final boolean isFlushAll)
    {
        if (!isInFlushing && lock.tryLock())
        {
            try
            {
                isInFlushing = true;
                flushExecutor.execute(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            if (isFlushAll)
                            {
                                flushAll();
                            }
                            else
                            {
                                flushLRU();
                            }
                        }
                        finally
                        {
                            isInFlushing = false;
                        }
                    }
                });
            }
            finally
            {
                lock.unlock();
            }
            return true;
        }
        return false;
    }

    private static void flushAll()
    {
        Map<Object, ConcurrentHashMap<Object, ConcurrentHashMap<Object, StatCounter>>> res = keys;
        keys = new ConcurrentHashMap<Object, ConcurrentHashMap<Object, ConcurrentHashMap<Object, StatCounter>>>(maxkeysize);
        try
        {
            Thread.sleep(5);
        }
        catch (InterruptedException e)
        {
        }
        statlog.info("[flushAll]size=" + res.size() + linesep);
        logWriter.writeLog(res);
        res = null;
    }
    private static final Comparator<Object[]> countsComparator = new Comparator<Object[]>()
    {
        @Override
        public int compare(Object[] keycount1, Object[] keycount2)
        {
            Long v1 = (Long) keycount1[1];
            Long v2 = (Long) keycount2[1];
            return v1.compareTo(v2);
        }
    };

    private static void flushLRU()
    {
        List<Object[]> counts = new ArrayList<Object[]>();
        for (Map.Entry<Object, ConcurrentHashMap<Object, ConcurrentHashMap<Object, StatCounter>>> e0 : keys.entrySet())
        {
            long count = 0;
            for (Map.Entry<Object, ConcurrentHashMap<Object, StatCounter>> e1 : e0.getValue().entrySet())
            {
                for (Map.Entry<Object, StatCounter> e2 : e1.getValue().entrySet())
                {
                    count += e2.getValue().getCount();
                }
            }
            counts.add(new Object[]
            {
                e0.getKey(), count
            });
        }
        statlog.debug("sortedSize=" + counts.size() + ",keys.size=" + keys.size() + linesep);// sortedSize=1135,keys.size=1169
        Collections.sort(counts, countsComparator);
        int i = 0;
        int remain = maxkeysize * 2 / 3;
        int flush = keys.size() - remain;
        Map<Object, ConcurrentHashMap<Object, ConcurrentHashMap<Object, StatCounter>>> flushed = new HashMap<Object, ConcurrentHashMap<Object, ConcurrentHashMap<Object, StatCounter>>>();
        for (Object[] keycount : counts)
        {
            Object key = keycount[0];
            ConcurrentHashMap<Object, ConcurrentHashMap<Object, StatCounter>> removed = keys.remove(key);
            if (removed != null)
            {
                flushed.put(key, removed);
                i++;
            }
            else
            {
                statlog.warn("-------------- Should not happen!!! ------------");
            }
            if (i >= flush)
            {
                if (keys.size() <= remain)
                {
                    break;
                }
            }
        }
        statlog.info("[flushLRU]flushedSize=" + flushed.size() + ",keys.size=" + keys.size() + linesep);
        logWriter.writeLog(flushed);
        flushed = null;
    }

    public static void main(String[] args) throws InterruptedException
    {
        for (int i = 0; i < 5000; i++)
        {
            BufferedStatLogWriter.add("appName1", "namespace1", "methodName1", 1, 10L);
            BufferedStatLogWriter.add("appName2", "namespace2", "methodName2", 2, 20L);
            BufferedStatLogWriter.add("appName3", "namespace3", "methodName3", 3, 30L);
            BufferedStatLogWriter.add("appName4", "namespace4", "methodName4", 4, 40L);
            if (i % 10 == 0)
            {
                Thread.sleep(1);
            }
        }
        BufferedStatLogWriter.flushLRU();
        Thread.sleep(10000);
    }
}
