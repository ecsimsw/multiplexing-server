package com.ecsimsw.server.domain;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServer implements Closeable {

    private final java.net.ServerSocket serverSocket;

    public WebServer(InetSocketAddress endpoint, int backlog) throws IOException {
        this.serverSocket = new ServerSocket();
        this.serverSocket.bind(endpoint, backlog);
    }

    public void run() {
        while (true) {
            try (final MySocket socket = new MySocket(serverSocket.accept())) {
                final String messageFromClient = socket.receive();
                final String response = response(messageFromClient);
                socket.send(response);
            } catch (IOException e) {
                throw new IllegalArgumentException("Socket error : " + e.getMessage());
            }
        }
    }

    private String response(String body) {
        return String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: " + body.getBytes().length + " ",
                "",
                body);
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }
}
