/*
 * Copyright (C) 2013 ferdi
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 */

package com.guang.eunormia.common.util;

import static com.guang.eunormia.common.util.Protocol.Keyword.AGGREGATE;
import static com.guang.eunormia.common.util.Protocol.Keyword.WEIGHTS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jun 28, 2013 5:29:31 PM
 * @version 0.0.1
*/
public class ZParams 
{
    public enum Aggregate {
        SUM, MIN, MAX;

        public final byte[] raw;

        Aggregate() {
            raw = SafeEncoder.encode(name());
        }
    }

    private List<byte[]> params = new ArrayList<byte[]>();

    public ZParams weights(final int... weights) {
        params.add(WEIGHTS.raw);
        for (final int weight : weights) {
            params.add(Protocol.toByteArray(weight));
        }

        return this;
    }

    public Collection<byte[]> getParams() {
        return Collections.unmodifiableCollection(params);
    }

    public ZParams aggregate(final Aggregate aggregate) {
        params.add(AGGREGATE.raw);
        params.add(aggregate.raw);
        return this;
    }
}
