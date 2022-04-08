package com.ecsimsw;

import com.ecsimsw.server.MultiPlexingServer;
import com.ecsimsw.server.WebServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import static com.ecsimsw.server.config.ServerConfig.*;

public class ServerApplication {

    public static void main(String[] args) throws IOException {
//        final InetSocketAddress endpoint = new InetSocketAddress(HOST_NAME, PORT);
//        try (WebServer webServer = new WebServer(endpoint, BACK_LOG)) {
//            webServer.run();
//        }

        MultiPlexingServer multiPlexingServer = new MultiPlexingServer();
        multiPlexingServer.run();
    }
}
