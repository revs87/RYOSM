package com.ryosm.core.com.ryosm.core;

import java.io.IOException;
import java.net.Socket;

import javax.net.SocketFactory;

/**
 * Created by revs on 10/09/2016.
 */
public class SocketManager extends Socket {

    private SocketFactory socketFactory;
    private Socket socket;

    public SocketManager() {

        newSocket();
    }

    private Socket newSocket() {
        socketFactory = SocketFactory.getDefault();
        try {
            socket = socketFactory.createSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return socket;
    }
}
