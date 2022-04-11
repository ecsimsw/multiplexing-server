package com.ecsimsw.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;

public interface WebServer extends Closeable {

    void init(InetSocketAddress endpoint, int backlog) throws IOException;

    void run() throws IOException, InterruptedException;

    @Override
    void close() throws IOException;
}
