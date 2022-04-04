package com.ecsimsw.server.http;

import com.ecsimsw.server.MessageHandler;
import com.ecsimsw.server.http.request.HttpRequest;
import com.ecsimsw.server.http.response.HttpResponse;

public class HttpMessageHandler implements MessageHandler {

    private final ServletContainer servletContainer = ServletContainer.init();

    @Override
    public String handle(String message) {
        final HttpRequest httpRequest = new HttpRequest(message);
        final HttpResponse httpResponse = new HttpResponse();

        servletContainer.service(httpRequest, httpResponse);
        return httpResponse.asString();
    }
}
