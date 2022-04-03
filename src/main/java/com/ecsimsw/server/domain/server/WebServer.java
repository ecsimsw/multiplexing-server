package com.ecsimsw.server.domain.server;

import com.ecsimsw.server.domain.http.HttpMessageHandler;
import com.ecsimsw.server.domain.server.socket.MySocket;
import com.ecsimsw.server.domain.server.socket.MyServerSocket;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class WebServer implements Closeable {

    private final ServerSocket serverSocket = new MyServerSocket();
    private final MessageHandler messageHandler = new HttpMessageHandler();

    public WebServer(InetSocketAddress endpoint, int backlog) throws IOException {
        this.serverSocket.bind(endpoint, backlog);
    }

    public void run() {
        while (true) {
            try (final MySocket socket = new MySocket(serverSocket.accept())) {
                final String message = socket.receive();
                final String response = messageHandler.handle(message);
                socket.send(response);
            } catch (IOException e) {
                System.out.println("Socket error : " + e.getMessage());
            }
        }
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }
}
