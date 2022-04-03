package com.ecsimsw.server.server.http;

import com.ecsimsw.server.server.MessageHandler;
import com.ecsimsw.server.server.http.request.HttpRequest;
import com.ecsimsw.server.server.http.response.HttpResponse;

public class HttpMessageHandler implements MessageHandler {

    private final ServletContainer servletContainer = new ServletContainer();

    @Override
    public String handle(String message) {
        final HttpRequest httpRequest = new HttpRequest(message);
        final HttpResponse httpResponse = new HttpResponse();

        servletContainer.service(httpRequest, httpResponse);
        return httpResponse.asString();
    }
}
