/*
 * Copyright (C) 2013 ferdi
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 */

package com.guang.eunormia.group;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 2, 2013 2:21:08 PM
 * @version 0.0.1
*/
public class EunormiaClusterTest 
{
    public static void main(String[] args) throws Exception    
    {
        EunormiaCluster cluster = new EunormiaCluster();
        
        cluster.setAppName("test");
        cluster.setVersion("v1.0");
        
        ZookeeperConfigManager cm = new ZookeeperConfigManager("test","v1.0");
        cm.setZkAddress("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183");
        cm.init();
        
        cluster.setConfigManager(cm);
        
        cluster.init();
        
        long start = System.currentTimeMillis();
        
        for(int i=0;i<1000000;i++)
        {
            cluster.getEunormia().set(Integer.toString(i).getBytes(),Integer.toString(i).getBytes());
        }
        
        long stop = System.currentTimeMillis();
        
        System.out.println(1000000000/(stop - start));
        
        System.exit(0);
        
    }
}
