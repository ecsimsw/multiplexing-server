package com.ecsimsw;

import com.ecsimsw.server.SingleThreadWebServer;
import com.ecsimsw.server.WebServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import static com.ecsimsw.server.config.ServerConfig.BACK_LOG;
import static com.ecsimsw.server.config.ServerConfig.PORT;

public class ServerApplication {

    public static void main(String[] args) {
        final InetAddress address = null;
        final InetSocketAddress endpoint = new InetSocketAddress(address, PORT);

        try (WebServer webServer = new SingleThreadWebServer()) {
            webServer.init(endpoint, BACK_LOG);
            webServer.run();
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }
}
