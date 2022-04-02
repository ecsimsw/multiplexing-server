package com.ecsimsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class MyServerSocket extends ServerSocket {

    public MyServerSocket() throws IOException {
        super();
        System.out.println("[SOCKET-INIT]");
    }

    @Override
    public void bind(SocketAddress endpoint, int backlog) throws IOException {
        super.bind(endpoint, backlog);
        System.out.println("[BIND/LISTEN] : " + endpoint);
    }

    @Override
    public Socket accept() throws IOException {
        final Socket accepted = super.accept();
        System.out.println("[ACCEPT] : " + accepted.getLocalAddress() + ":" + accepted.getPort());
        return accepted;
    }

    @Override
    public void close() throws IOException {
        super.close();
        System.out.println("[CLOSE]");
    }
}