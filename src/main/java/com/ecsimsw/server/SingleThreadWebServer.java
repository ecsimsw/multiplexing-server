package com.ecsimsw.server;

import com.ecsimsw.server.http.ServletContainer;
import com.ecsimsw.server.http.request.HttpRequest;
import com.ecsimsw.server.http.response.HttpResponse;
import com.ecsimsw.server.socket.MyServerSocket;
import com.ecsimsw.server.socket.MySocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class SingleThreadWebServer implements WebServer {

    private final ServerSocket serverSocket;
    private final ServletContainer container;

    public SingleThreadWebServer() throws IOException {
        this.serverSocket = MyServerSocket.init();
        this.container = ServletContainer.init();
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
                handle(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handle(MySocket socket) {
        try {
            System.out.println("[4. RECV, SEND]");

            final HttpRequest httpRequest = new HttpRequest(socket.receive());
            final HttpResponse httpResponse = new HttpResponse(httpRequest.getHttpVersion());

            container.execute(httpRequest, httpResponse);

            socket.send(httpResponse.asString());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }
}
