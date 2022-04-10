package com.ecsimsw.server.http;

import com.ecsimsw.server.config.ServerConfig;
import com.ecsimsw.server.http.exception.BadRequestException;
import com.ecsimsw.server.http.exception.NotFoundException;
import com.ecsimsw.server.http.request.HttpRequest;
import com.ecsimsw.server.http.response.HttpResponse;
import com.ecsimsw.server.http.servlet.DefaultServlet;
import com.ecsimsw.server.http.servlet.Servlet;
import java.util.HashMap;
import java.util.Map;

public class ServletContainer {

    private final DefaultServlet defaultServlet;
    private final Map<String, Servlet> container;

    private ServletContainer(Map<String, Servlet> container) {
        this.container = container;
        this.defaultServlet = new DefaultServlet();
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
            e.printStackTrace();
            throw new IllegalArgumentException("ERROR in load servlet container");
        }
    }

    public void execute(HttpRequest request, HttpResponse response) {
        try {
            System.out.println("[4. RECV, SEND]");
            final Servlet servlet = findServlet(request);
            servlet.doService(request, response);
        } catch (BadRequestException badRequestException) {
            defaultServlet.badRequest(response);
        } catch (NotFoundException notFoundException) {
            defaultServlet.notFoundException(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Servlet findServlet(HttpRequest httpRequest) {
        if (container.containsKey(httpRequest.getPath())) {
            return container.get(httpRequest.getPath());
        }
        return defaultServlet;
    }
}
