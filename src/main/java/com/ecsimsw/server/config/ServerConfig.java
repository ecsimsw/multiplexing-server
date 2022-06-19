package com.ecsimsw.server.config;

import com.ecsimsw.server.http.servlet.HelloServlet;
import com.ecsimsw.server.http.servlet.IndexServlet;
import com.ecsimsw.server.http.servlet.ServerInfoServlet;
import com.ecsimsw.server.http.servlet.UserCountServlet;

import java.util.HashMap;
import java.util.Map;

public class ServerConfig {

    public static final String INDEX_FILE_PATH = "/index.html";
    public static final String BAD_REQUEST_FILE_PATH = "/bad_request.html";
    public static final String NOT_FOUND_FILE_PATH = "/not_found.html";
    public static final String USER_COUNT_FILE_PATH = "/user_count.html";

    public static final int PORT = 8080;
    public static final int BACK_LOG = 50;

    public static final Map<String, Class<?>> SERVLET_MAPPINGS = new HashMap<>();

    static {
        SERVLET_MAPPINGS.put("/", IndexServlet.class);
        SERVLET_MAPPINGS.put("/userCount", UserCountServlet.class);
        SERVLET_MAPPINGS.put("/info1", ServerInfoServlet.class);
        SERVLET_MAPPINGS.put("/info2", ServerInfoServlet.class);
        SERVLET_MAPPINGS.put("/info3", ServerInfoServlet.class);
        SERVLET_MAPPINGS.put("/info4", ServerInfoServlet.class);
        SERVLET_MAPPINGS.put("/info5", ServerInfoServlet.class);
        SERVLET_MAPPINGS.put("/info6", ServerInfoServlet.class);
        SERVLET_MAPPINGS.put("/hello", HelloServlet.class);
    }
}
