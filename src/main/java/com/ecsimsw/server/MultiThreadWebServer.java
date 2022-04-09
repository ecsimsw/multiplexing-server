package com.ecsimsw.server;

import com.ecsimsw.server.http.ServletContainer;
import com.ecsimsw.server.http.request.HttpRequest;
import com.ecsimsw.server.http.response.HttpResponse;
import com.ecsimsw.server.socket.MyServerSocket;
import com.ecsimsw.server.socket.MySocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class MultiThreadWebServer implements WebServer {

    private final ServerSocket serverSocket;
    private final ServletContainer servletContainer;

    public MultiThreadWebServer() throws IOException {
        this.serverSocket = MyServerSocket.init();
        this.servletContainer = ServletContainer.init();
    }

    @Override
    public void init(InetSocketAddress endpoint, int backlog) throws IOException {
        this.serverSocket.bind(endpoint, backlog);
    }

    @Override
    public void run() {
        while (true) {
            try {
                final MySocket socket = new MySocket(serverSocket.accept());
                final RunnableHandler handler = new RunnableHandler(servletContainer, socket);
                final Thread thread = new Thread(handler);
                thread.start();
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

class RunnableHandler implements Runnable {

    private final ServletContainer container;
    private final MySocket socket;

    public RunnableHandler(ServletContainer container, MySocket socket) {
        this.container = container;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            final HttpRequest httpRequest = new HttpRequest(socket.receive());
            final HttpResponse httpResponse = new HttpResponse(httpRequest.getHttpVersion());

            container.execute(httpRequest, httpResponse);

            socket.send(httpResponse.asString());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
