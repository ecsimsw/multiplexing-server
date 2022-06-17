package com.ecsimsw.server.http.servlet;

import com.ecsimsw.server.http.exception.NotFoundException;
import com.ecsimsw.server.http.request.HttpRequest;
import com.ecsimsw.server.http.response.HttpResponse;

import java.io.IOException;
import java.net.InetAddress;

public class ServerInfoServlet extends Servlet {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            final InetAddress local = InetAddress.getLocalHost();
            final String ip = local.getHostAddress();
            httpResponse.ok(ip);
        } catch (IOException e) {
            throw new NotFoundException("no file");
        }
    }
}
