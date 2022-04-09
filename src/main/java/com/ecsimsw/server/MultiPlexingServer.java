package com.ecsimsw.server;

import com.ecsimsw.server.http.ServletContainer;
import com.ecsimsw.server.http.request.HttpRequest;
import com.ecsimsw.server.http.response.HttpResponse;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class MultiPlexingServer implements WebServer {

    private final ServletContainer servletContainer;
    private final Selector selector;
    private final ServerSocketChannel serverSocket;

    public MultiPlexingServer() throws IOException {
        this.selector = Selector.open();
        this.serverSocket = ServerSocketChannel.open();
        this.servletContainer = ServletContainer.init();
        System.out.println("[1. SOCKET-INIT] : init server socket");
    }

    @Override
    public void init(InetSocketAddress endpoint, int backlog) throws IOException {
        this.serverSocket.bind(endpoint);
        this.serverSocket.configureBlocking(false);
        this.serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("[2. BIND/LISTEN] : " + endpoint);
    }

    @Override
    public void run() throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocate(256);

        while (true) {
            System.out.println("[3. SELECT & ACCEPT] ");
            selector.select();

            final Set<SelectionKey> selectionKeys = selector.selectedKeys();
            final Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                final SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isAcceptable()) {
                    accept(selector, serverSocket);
                }

                if (key.isReadable()) {
                    handle(buffer, key);
                }
            }
        }
    }

    private void accept(Selector selector, ServerSocketChannel serverSocket) throws IOException {
        System.out.println("New client connected...");

        final SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    private void handle(ByteBuffer buffer, SelectionKey key) throws IOException {
        System.out.println("Handle... ");

        try (SocketChannel client = (SocketChannel) key.channel()) {
            final HttpRequest httpRequest = new HttpRequest(readMessage(buffer, client));
            final HttpResponse httpResponse = new HttpResponse(httpRequest.getHttpVersion());

            servletContainer.execute(httpRequest, httpResponse);

            client.write(StandardCharsets.UTF_8.encode(httpResponse.asString()));
            buffer.clear();
        }
    }

    private String readMessage(ByteBuffer buffer, SocketChannel client) throws IOException {
        client.read(buffer);
        buffer.flip();

        String message = "";
        while (buffer.hasRemaining()) {
            message = StandardCharsets.UTF_8.decode(buffer).toString();
        }
        return message;
    }

    @Override
    public void close() throws IOException {
        selector.close();
        serverSocket.close();
        System.out.println("[5. SERVER-CLOSE] : close server socket");
    }
}
