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

import com.guang.eunormia.common.core.BaseCommands;
import static com.guang.eunormia.common.util.Protocol.Keyword.BY;
import static com.guang.eunormia.common.util.Protocol.Keyword.NOSORT;
import static com.guang.eunormia.common.util.Protocol.Keyword.DESC;
import static com.guang.eunormia.common.util.Protocol.Keyword.ASC;
import static com.guang.eunormia.common.util.Protocol.Keyword.LIMIT;
import static com.guang.eunormia.common.util.Protocol.Keyword.ALPHA;
import static com.guang.eunormia.common.util.Protocol.Keyword.GET;

import com.guang.eunormia.common.serializer.StringEunormiaSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jun 28, 2013 5:13:49 PM
 * @version 0.0.1
*/
public class SortParams 
{
    private List<byte[]> params = new ArrayList<byte[]>();
    protected StringEunormiaSerializer stringSerializer = new StringEunormiaSerializer();

    public SortParams by(final int namespace, final String pattern) {
        return by(namespace, SafeEncoder.encode(pattern));
    }

    public SortParams by(final int namespace, final byte[] pattern) {
        params.add(BY.raw);
        params.add(rawKey(namespace, pattern));
        return this;
    }

    private byte[] rawKey(int namespace, byte[] bytekey) {
        byte[] prefix = stringSerializer.serialize(String.valueOf(namespace));
        byte[] result = new byte[prefix.length + bytekey.length + 1];
        System.arraycopy(prefix, 0, result, 0, prefix.length);
        System.arraycopy(BaseCommands.PART, 0, result, prefix.length, 1);
        System.arraycopy(bytekey, 0, result, prefix.length + 1, bytekey.length);
        return result;
    }

    public SortParams nosort() {
        params.add(BY.raw);
        params.add(NOSORT.raw);
        return this;
    }

    public Collection<byte[]> getParams() {
        return Collections.unmodifiableCollection(params);
    }

    public SortParams desc() {
        params.add(DESC.raw);
        return this;
    }

    public SortParams asc() {
        params.add(ASC.raw);
        return this;
    }

    public SortParams limit(final int start, final int count) {
        params.add(LIMIT.raw);
        params.add(Protocol.toByteArray(start));
        params.add(Protocol.toByteArray(count));
        return this;
    }

    public SortParams alpha() {
        params.add(ALPHA.raw);
        return this;
    }

    public SortParams get(int namespace, String... patterns) {
        for (final String pattern : patterns) {
            params.add(GET.raw);
            params.add(rawKey(namespace, SafeEncoder.encode(pattern)));
        }
        return this;
    }

    public SortParams get(int namespace, byte[]... patterns) {
        for (final byte[] pattern : patterns) {
            params.add(GET.raw);
            params.add(rawKey(namespace, pattern));
        }
        return this;
    }
}
