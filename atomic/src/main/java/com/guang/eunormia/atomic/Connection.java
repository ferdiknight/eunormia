/*
 * Copyright (C) 2013 ferdi
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 */
package com.guang.eunormia.atomic;

import com.guang.eunormia.common.EunormiaConnectionException;
import com.guang.eunormia.common.EunormiaDataException;
import com.guang.eunormia.common.EunormiaException;
import com.guang.eunormia.common.util.Protocol;
import com.guang.eunormia.common.util.Protocol.Command;
import com.guang.eunormia.common.util.RedisInputStream;
import com.guang.eunormia.common.util.RedisOutputStream;
import com.guang.eunormia.common.util.SafeEncoder;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 2:19:43 PM
 * @version 0.0.1
 */
public class Connection
{

    private String host;
    private int port = Protocol.DEFAULT_PORT;
    private Socket socket;
    private Protocol protocol = new Protocol();
    private RedisOutputStream outputStream;
    private RedisInputStream inputStream;
    private int pipelinedCommands = 0;
    private int timeout = Protocol.DEFAULT_TIMEOUT;

    public Socket getSocket()
    {
        return socket;
    }

    public int getTimeout()
    {
        return timeout;
    }

    public void setTimeout(final int timeout)
    {
        this.timeout = timeout;
    }

    public void setTimeoutInfinite()
    {
        try
        {
            socket.setKeepAlive(true);
            socket.setSoTimeout(0);
        }
        catch (SocketException ex)
        {
            throw new EunormiaException(ex);
        }
    }

    public void rollbackTimeout()
    {
        try
        {
            socket.setSoTimeout(timeout);
            socket.setKeepAlive(false);
        }
        catch (SocketException ex)
        {
            throw new EunormiaException(ex);
        }
    }

    public Connection(final String host)
    {
        super();
        this.host = host;
    }

    protected void flush()
    {
        try
        {
            outputStream.flush();
        }
        catch (IOException e)
        {
            throw new EunormiaConnectionException(e);
        }
    }

    protected Connection sendCommand(final Command cmd, final String... args)
    {
        final byte[][] bargs = new byte[args.length][];
        for (int i = 0; i < args.length; i++)
        {
            bargs[i] = SafeEncoder.encode(args[i]);
        }
        return sendCommand(cmd, bargs);
    }

    protected Connection sendCommand(final Command cmd, final byte[]... args)
    {
        connect();
        protocol.sendCommand(outputStream, cmd, args);
        pipelinedCommands++;
        return this;
    }

    protected Connection sendCommand(final Command cmd)
    {
        connect();
        protocol.sendCommand(outputStream, cmd, new byte[0][]);
        pipelinedCommands++;
        return this;
    }

    public Connection(final String host, final int port)
    {
        super();
        this.host = host;
        this.port = port;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(final String host)
    {
        this.host = host;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(final int port)
    {
        this.port = port;
    }

    public Connection()
    {
    }

    public void connect()
    {
        if (!isConnected())
        {
            try
            {
                socket = new Socket();
                socket.setReuseAddress(true);
                socket.setKeepAlive(true); // Will monitor the TCP connection is
                // valid
                socket.setTcpNoDelay(true); // Socket buffer Whetherclosed, to
                // ensure timely delivery of data
                socket.setSoLinger(true, 0); // Control calls close () method,
                // the underlying socket is closed
                // immediately

                socket.connect(new InetSocketAddress(host, port), timeout);
                socket.setSoTimeout(timeout);
                outputStream = new RedisOutputStream(socket.getOutputStream());
                inputStream = new RedisInputStream(socket.getInputStream());
            }
            catch (IOException ex)
            {
                throw new EunormiaConnectionException(ex);
            }
        }
    }

    public void disconnect()
    {
        if (isConnected())
        {
            try
            {
                inputStream.close();
                outputStream.close();
                if (!socket.isClosed())
                {
                    socket.close();
                }
            }
            catch (IOException ex)
            {
                throw new EunormiaConnectionException(ex);
            }
        }
    }

    public boolean isConnected()
    {
        return socket != null && socket.isBound() && !socket.isClosed() && socket.isConnected() && !socket.isInputShutdown() && !socket.isOutputShutdown();
    }

    protected String getStatusCodeReply()
    {
        flush();
        pipelinedCommands--;
        final byte[] resp = (byte[]) protocol.read(inputStream);
        if (null == resp)
        {
            return null;
        }
        else
        {
            return SafeEncoder.encode(resp);
        }
    }

    public String getBulkReply()
    {
        final byte[] result = getBinaryBulkReply();
        if (null != result)
        {
            return SafeEncoder.encode(result);
        }
        else
        {
            return null;
        }
    }

    public byte[] getBinaryBulkReply()
    {
        flush();
        pipelinedCommands--;
        return (byte[]) protocol.read(inputStream);
    }

    public Long getIntegerReply()
    {
        flush();
        pipelinedCommands--;
        return (Long) protocol.read(inputStream);
    }

    public List<String> getMultiBulkReply()
    {
        return BuilderFactory.STRING_LIST.build(getBinaryMultiBulkReply());
    }

    @SuppressWarnings("unchecked")
    public List<byte[]> getBinaryMultiBulkReply()
    {
        flush();
        pipelinedCommands--;
        return (List<byte[]>) protocol.read(inputStream);
    }

    @SuppressWarnings("unchecked")
    public List<Object> getObjectMultiBulkReply()
    {
        flush();
        pipelinedCommands--;
        return (List<Object>) protocol.read(inputStream);
    }

    public List<Object> getAll()
    {
        return getAll(0);
    }

    public List<Object> getAll(int except)
    {
        List<Object> all = new ArrayList<Object>();
        flush();
        while (pipelinedCommands > except)
        {
            try
            {
                all.add(protocol.read(inputStream));
            }
            catch (EunormiaDataException e)
            {
                all.add(e);
            }
            pipelinedCommands--;
        }
        return all;
    }

    public Object getOne()
    {
        flush();
        pipelinedCommands--;
        return protocol.read(inputStream);
    }
}
