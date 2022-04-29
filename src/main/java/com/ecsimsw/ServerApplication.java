package com.ecsimsw;

import com.ecsimsw.server.MultiThreadWebServer;
import com.ecsimsw.server.MultiplexingWebServer;
import com.ecsimsw.server.WebServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import static com.ecsimsw.server.config.ServerConfig.*;

public class ServerApplication {

    public static void main(String[] args) {
        final InetSocketAddress endpoint = new InetSocketAddress(PORT);

        try (WebServer webServer = new MultiThreadWebServer()) {
            webServer.init(endpoint, BACK_LOG);
            webServer.run();
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }
}
