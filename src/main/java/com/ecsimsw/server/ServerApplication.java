package com.ecsimsw.server;

import com.ecsimsw.server.domain.WebServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class ServerApplication {

    public static void main(String[] args) throws IOException {
        final int port = 8080;
        final InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getLocalHost(), port);
        final int backlog = 50;

        try (WebServer webServer = new WebServer(socketAddress, backlog)) {
            webServer.run();
        }
    }
}
