package com.ecsimsw.server;

import com.ecsimsw.server.config.ServerConfig;
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

public class MultiPlexingServer {

    public void run() throws IOException {
        final Selector selector = Selector.open();
        final ServerSocketChannel serverSocket = ServerSocketChannel.open();

        serverSocket.bind(new InetSocketAddress(ServerConfig.HOST_NAME, ServerConfig.PORT));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        final ByteBuffer buffer = ByteBuffer.allocate(256);

        while (true) {
            System.out.println("Waiting for select...");
            selector.select();

            final Set<SelectionKey> selectionKeys = selector.selectedKeys();
            final Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                final SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isAcceptable()) accept(selector, serverSocket);
                if (key.isReadable()) handle(buffer, key);
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
        try (SocketChannel client = (SocketChannel) key.channel()) {
            client.read(buffer);
            buffer.flip();

            final HttpResponse httpResponse = new HttpResponse("Http/1.1");
            httpResponse.ok("<html>hi</html>");

            client.write(StandardCharsets.UTF_8.encode(httpResponse.asString()));
            buffer.clear();
        }
    }
}
