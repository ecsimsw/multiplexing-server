package com.ecsimsw.server.server.http;

import com.ecsimsw.server.server.MessageHandler;
import com.ecsimsw.server.server.http.servlet.Servlet;
import com.ecsimsw.server.server.http.servlet.ServletContainer;

public class HttpMessageHandler implements MessageHandler {

    private final ServletContainer servletContainer = new ServletContainer();

    @Override
    public String handle(String message) {
        final HttpRequest httpRequest = new HttpRequest(message);
        final HttpResponse httpResponse = new HttpResponse();

        final Servlet servlet = servletContainer.findServlet(httpRequest);
        servlet.doService(httpRequest, httpResponse);
        return httpResponse.asString();
    }
}
