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

import com.guang.eunormia.common.core.EunormiaManager;
import com.guang.eunormia.common.serializer.StringEunormiaSerializer;
import com.guang.eunormia.group.commands.EunormiaManagerFactory;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 2, 2013 3:13:03 PM
 * @version 0.0.1
*/
public class DemoTest 
{
    
    public static void main(String[] args)    
    {
        EunormiaManager<String> manager = EunormiaManagerFactory.create("test", "v1.0","127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", new StringEunormiaSerializer());
        
        
        
        long start = System.currentTimeMillis();
        
        for(int i=0;i<1000000;i++)
        {
        
            manager.getValueCommands().get(0,"test" + i%10);
        }
        
        long stop = System.currentTimeMillis();
        
        System.out.println(1000000000/(stop - start));
        
        System.exit(0);
        
    }
}
