/*
 * Copyright (C) 2013 ferdi
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 */

package com.guang.eunormia.common.config;

import com.guang.eunormia.common.Node;
import com.guang.eunormia.common.config.HAConfig.ServerProperties;
import java.util.List;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jun 28, 2013 4:57:33 PM
 * @version 0.0.1
*/
public interface Router 
{
    public final class RouteData {

        public int[] weights;
        public Node[] group;
        public List<ServerProperties> props;

        public RouteData(List<ServerProperties> props, int[] weights, Node[] group) {
            this.props = props;
            this.weights = weights;
            this.group = group;
        }

        @Override
        public String toString() {
            return "RouteData{" + "weights=" + weights + ", group=" + group + ", props=" + props + '}';
        }
    }

    Node route() throws Exception;

    RouteData getReadData();

    RouteData getWriteData();

    RouteData getAllRouteData();

    void onError(Node single);

    Node getAtomic(String key);

    void destroy();

}
