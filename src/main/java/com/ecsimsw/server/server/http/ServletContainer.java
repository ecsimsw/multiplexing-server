package com.ecsimsw.server.server.http;

import com.ecsimsw.server.server.http.request.HttpRequest;
import com.ecsimsw.server.server.http.response.HttpResponse;
import com.ecsimsw.server.server.http.servlet.DefaultServlet;
import com.ecsimsw.server.server.http.servlet.IndexServlet;
import com.ecsimsw.server.server.http.servlet.Servlet;
import com.ecsimsw.server.server.http.exception.BadRequestException;
import com.ecsimsw.server.server.http.exception.NotFoundException;
import com.ecsimsw.server.server.http.servlet.UserCountServlet;

import java.util.HashMap;
import java.util.Map;

public class ServletContainer {

    private static final DefaultServlet DEFAULT_SERVLET = new DefaultServlet();

    private final Map<String, Servlet> container = new HashMap<>();

    {
        container.put("/", new IndexServlet());
        container.put("/userCount", new UserCountServlet());
    }

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            final Servlet servlet = findServlet(httpRequest);
            servlet.doService(httpRequest, httpResponse);
        } catch (BadRequestException badRequestException) {
            DEFAULT_SERVLET.badRequest(httpRequest, httpResponse);
        } catch (NotFoundException notFoundException) {
            DEFAULT_SERVLET.notFoundException(httpRequest, httpResponse);
        }
    }

    private Servlet findServlet(HttpRequest httpRequest) {
        if (!container.containsKey(httpRequest.getPath())) {
            return DEFAULT_SERVLET;
        }
        return container.get(httpRequest.getPath());
    }
}
