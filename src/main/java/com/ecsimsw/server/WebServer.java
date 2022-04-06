package com.ecsimsw.server;

import com.ecsimsw.server.http.ServletContainer;
import com.ecsimsw.server.socket.MyServerSocket;
import com.ecsimsw.server.socket.MySocket;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class WebServer implements Closeable {

    private final ServerSocket serverSocket;
    private final ServletContainer servletContainer;

    public WebServer(InetSocketAddress endpoint, int backlog) throws IOException {
        this.serverSocket = MyServerSocket.init();
        this.serverSocket.bind(endpoint, backlog);
        this.servletContainer = ServletContainer.init();
    }

    public void run() {
        while (true) {
            try {
                final MySocket socket = new MySocket(serverSocket.accept());
                servletContainer.execute(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }
}
