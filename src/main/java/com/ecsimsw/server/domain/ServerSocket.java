package com.ecsimsw.server.domain;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

public class ServerSocket extends java.net.ServerSocket {

    public ServerSocket() throws IOException {
        super();
        System.out.println("[SOCKET-INIT] : init server socket");
    }

    @Override
    public void bind(SocketAddress endpoint, int backlog) throws IOException {
        super.bind(endpoint, backlog);
        System.out.println("[BIND/LISTEN] : bind " + endpoint);
    }

    @Override
    public Socket accept() throws IOException {
        final Socket accepted = super.accept();
        System.out.println("[ACCEPT     ] : " + accepted.getLocalAddress() + ":" + accepted.getPort());
        return accepted;
    }

    @Override
    public void close() throws IOException {
        super.close();
        System.out.println("[CLOSE      ] : close server socket");
    }
}
