/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation.
 *
 */
package com.guang.eunormia.common;

/**
 * EunormiaException Object
 * @author ferdi email:ferdi@blueferdi.com
 * @since 2013-06-28 14:54:00
 * @version 0.0.1
 */
public class EunormiaException extends RuntimeException
{
    
    
    public EunormiaException(String message)
    {
        super(message);
    }

    public EunormiaException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public EunormiaException(Throwable cause)
    {
        super(cause);
    }
    
}
