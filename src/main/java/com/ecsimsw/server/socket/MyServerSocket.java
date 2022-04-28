package com.ecsimsw.server.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class MyServerSocket extends ServerSocket {

    public MyServerSocket() throws IOException {
        super();
    }

    public static MyServerSocket init() throws IOException {
//        System.out.println("[1. SOCKET-INIT] : init server socket");
        return new MyServerSocket();
    }

    @Override
    public void bind(SocketAddress endpoint, int backlog) throws IOException {
        super.bind(endpoint, backlog);
//        System.out.println("[2. BIND/LISTEN] : " + endpoint);
    }

    @Override
    public Socket accept() throws IOException {
        final Socket accepted = super.accept();
//        System.out.println("[3. ACCEPT] " + accepted.getLocalAddress() + ":" + accepted.getPort());
        return accepted;
    }

    @Override
    public void close() throws IOException {
        super.close();
//        System.out.println("[5. SERVER-CLOSE] : close server socket");
    }
}
