package com.ecsimsw.server.http;

import com.ecsimsw.server.config.ServerConfig;
import com.ecsimsw.server.http.exception.BadRequestException;
import com.ecsimsw.server.http.exception.NotFoundException;
import com.ecsimsw.server.http.request.HttpRequest;
import com.ecsimsw.server.http.response.HttpResponse;
import com.ecsimsw.server.http.servlet.DefaultServlet;
import com.ecsimsw.server.http.servlet.Servlet;
import com.ecsimsw.server.socket.MySocket;

import java.io.IOException;
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

    public void execute(MySocket socket) {
        final RunnableHandler handler = new RunnableHandler(defaultServlet, container, socket);
        final Thread handleThread = new Thread(handler);
        handleThread.start();
    }
}

class RunnableHandler implements Runnable {

    private final DefaultServlet defaultServlet;
    private final Map<String, Servlet> container;
    private final MySocket socket;

    public RunnableHandler(DefaultServlet defaultServlet, Map<String, Servlet> container, MySocket socket) {
        this.defaultServlet = defaultServlet;
        this.container = container;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            final HttpRequest httpRequest = new HttpRequest(socket.receive());
            final HttpResponse httpResponse = new HttpResponse(httpRequest.getHttpVersion());

            service(httpRequest, httpResponse);

            Thread.sleep(10000L);
            socket.send(httpResponse.asString());
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Servlet findServlet(HttpRequest httpRequest) {
        if (container.containsKey(httpRequest.getPath())) {
            return container.get(httpRequest.getPath());
        }
        return defaultServlet;
    }

    private void service(HttpRequest request, HttpResponse response) {
        try {
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
}
