package com.ecsimsw.server.server.http.servlet;

import com.ecsimsw.server.server.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

public class ServletContainer {

    private static final Servlet SERVLET_NOT_FOUND = null;

    private final Map<String, Servlet> container = new HashMap<>();

    {

    }

    public Servlet findServlet(HttpRequest httpRequest) {
        if(!container.containsKey(httpRequest.getPath())) {
            return SERVLET_NOT_FOUND;
        }
        return container.get(httpRequest.getPath());
    }
}
