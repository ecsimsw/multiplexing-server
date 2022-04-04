package com.ecsimsw.server.http;

import com.ecsimsw.server.ServerConfig;
import com.ecsimsw.server.http.exception.BadRequestException;
import com.ecsimsw.server.http.exception.NotFoundException;
import com.ecsimsw.server.http.request.HttpRequest;
import com.ecsimsw.server.http.response.HttpResponse;
import com.ecsimsw.server.http.servlet.DefaultServlet;
import com.ecsimsw.server.http.servlet.Servlet;

import java.util.HashMap;
import java.util.Map;

public class ServletContainer {

    private static final DefaultServlet DEFAULT_SERVLET = new DefaultServlet();

    private final Map<String, Servlet> container;

    private ServletContainer(Map<String, Servlet> container) {
        this.container = container;
    }

    public static ServletContainer init() {
        try {
            final Map<String, Servlet> container = new HashMap<>();
            final Map<String, Class<?>> servletMappings = ServerConfig.servletMappings;
            for (String path : servletMappings.keySet()) {
                final Class<?> aClass = servletMappings.get(path);
                final Servlet servlet = (Servlet) aClass.getConstructor().newInstance();
                container.put(path, servlet);
            }
            return new ServletContainer(container);
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR in load servlet container");
        }
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
