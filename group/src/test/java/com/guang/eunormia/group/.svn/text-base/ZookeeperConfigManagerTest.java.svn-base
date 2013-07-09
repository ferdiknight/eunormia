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

import com.guang.eunormia.common.Node;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 2, 2013 10:44:26 AM
 * @version 0.0.1
*/
public class ZookeeperConfigManagerTest 
{
    public static void main(String[] args) throws Exception    
    {
        
        ZookeeperConfigManager cm = new ZookeeperConfigManager("test","v1.0");
        cm.setZkAddress("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183");
        cm.init();
        
        
        long start = System.currentTimeMillis();
        
        for(int i=0;i<1000000;i++)
        {
            Node[] nodes = cm.getRouter().getWriteData().group;
            
            for(Node node : nodes)
            {
                node.getEunormia().set(Integer.toString(i).getBytes(),Integer.toString(i).getBytes());
            }
            
//            if(!Integer.toString(i).equals(new String(cm.getRouter().route().getEunormia().get(Integer.toString(i).getBytes()))))
//            {
//                System.out.println(false);
//            }
            
//            for(Node node : nodes)
//            {
//                node.getEunormia().del(Integer.toString(i).getBytes());
//            }
        }
        
        long stop = System.currentTimeMillis();
        
        System.out.println(stop - start);
        
        System.out.println(1000000000/(stop-start));
        
        System.exit(0);
        
    }
}
