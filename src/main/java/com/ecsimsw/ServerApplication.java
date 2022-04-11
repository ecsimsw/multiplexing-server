package com.ecsimsw;

import static com.ecsimsw.server.config.ServerConfig.BACK_LOG;
import static com.ecsimsw.server.config.ServerConfig.HOST_NAME;
import static com.ecsimsw.server.config.ServerConfig.PORT;

import com.ecsimsw.server.MultiplexingWebServer;
import com.ecsimsw.server.WebServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerApplication {

    public static void main(String[] args) {
        final InetSocketAddress endpoint = new InetSocketAddress(HOST_NAME, PORT);

        try (WebServer webServer = new MultiplexingWebServer()) {
            webServer.init(endpoint, BACK_LOG);
            webServer.run();
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }
}
