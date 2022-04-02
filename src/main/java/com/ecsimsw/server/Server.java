package com.ecsimsw.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {

        try (final ServerSocket serverSocket = new MyServerSocket()) {
            final InetSocketAddress endpoint = new InetSocketAddress(InetAddress.getLocalHost(), 8080);
            final int backlog = 50;
            serverSocket.bind(endpoint, backlog);

            try (final MySocket socket = new MySocket(serverSocket.accept())) {
                final String messageFromClient = socket.receive();
                socket.send(messageFromClient);
            }
        }
    }
}
