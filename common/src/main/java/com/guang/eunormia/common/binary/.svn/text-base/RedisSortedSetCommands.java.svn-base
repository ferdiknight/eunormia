/*
 * Copyright (C) 2013 ferdi
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 */

package com.guang.eunormia.common.binary;

import com.guang.eunormia.common.config.Process;
import com.guang.eunormia.common.config.Process.Policy;
import com.guang.eunormia.common.util.ZParams;
import java.util.Set;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jun 28, 2013 5:28:51 PM
 * @version 0.0.1
*/
public interface RedisSortedSetCommands 
{
    public enum Aggregate {
        SUM, MIN, MAX;
    }

    public class Tuple {
        private final double score;
        private final byte[] value;

        public Tuple(byte[] value, double score) {
            this.score = score;
            this.value = value;
        }

        public double getScore() {
            return score;
        }

        public byte[] getValue() {
            return value;
        }
    }

    @Process(Policy.WRITE)
    Boolean zAdd(byte[] key, double score, byte[] value);

    @Process(Policy.WRITE)
    Long zAdd(byte[] key, Tuple... value);

    @Process(Policy.WRITE)
    Long zRem(byte[] key, byte[]... value);

    @Process(Policy.WRITE)
    Double zIncrBy(byte[] key, double increment, byte[] value);

    @Process(Policy.READ)
    Long zRank(byte[] key, byte[] value);

    @Process(Policy.READ)
    Long zRevRank(byte[] key, byte[] value);

    @Process(Policy.READ)
    Set<byte[]> zRange(byte[] key, long begin, long end);

    @Process(Policy.READ)
    Set<Tuple> zRangeWithScore(byte[] key, long begin, long end);

    @Process(Policy.READ)
    Set<byte[]> zRevRange(byte[] key, long begin, long end);

    @Process(Policy.READ)
    Set<Tuple> zRevRangeWithScore(byte[] key, long begin, long end);

    @Process(Policy.READ)
    Set<byte[]> zRangeByScore(byte[] key, double min, double max);

    @Process(Policy.READ)
    Set<Tuple> zRangeByScoreWithScore(byte[] key, double min, double max);

    @Process(Policy.READ)
    Set<byte[]> zRangeByScore(byte[] key, double min, double max, long offset, long count);

    @Process(Policy.READ)
    Set<Tuple> zRangeByScoreWithScore(byte[] key, double min, double max, long offset, long count);

    @Process(Policy.READ)
    Set<byte[]> zRevRangeByScore(byte[] key, double min, double max);

    @Process(Policy.READ)
    Set<Tuple> zRevRangeByScoreWithScore(byte[] key, double min, double max);

    @Process(Policy.READ)
    Set<byte[]> zRevRangeByScore(byte[] key, double min, double max, long offset, long count);

    @Process(Policy.READ)
    Set<Tuple> zRevRangeByScoreWithScore(byte[] key, double min, double max, long offset, long count);

    @Process(Policy.READ)
    Long zCount(byte[] key, double min, double max);

    @Process(Policy.READ)
    Long zCard(byte[] key);

    @Process(Policy.READ)
    Double zScore(byte[] key, byte[] value);

    @Process(Policy.WRITE)
    Long zRemRange(byte[] key, long begin, long end);

    @Process(Policy.WRITE)
    Long zRemRangeByScore(byte[] key, double min, double max);

    @Process(Policy.WRITE)
    Long zUnionStore(byte[] destKey, byte[]... sets);

    @Process(Policy.WRITE)
    Long zUnionStore(byte[] destKey, ZParams params, int[] weights, byte[]... sets);

    @Process(Policy.WRITE)
    Long zInterStore(byte[] destKey, byte[]... sets);

    @Process(Policy.WRITE)
    Long zInterStore(byte[] destKey, ZParams aggregate, int[] weights, byte[]... sets);
}
