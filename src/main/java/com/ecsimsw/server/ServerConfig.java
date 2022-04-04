package com.ecsimsw.server;

import com.ecsimsw.server.http.servlet.IndexServlet;
import com.ecsimsw.server.http.servlet.UserCountServlet;

import java.util.HashMap;
import java.util.Map;

public class ServerConfig {

    public static final String INDEX_FILE_PATH = "/index.html";
    public static final String BAD_REQUEST_FILE_PATH = "/bad_request.html";
    public static final String NOT_FOUND_FILE_PATH = "/not_found.html";
    public static final String USER_COUNT_FILE_PATH = "/user_count.html";

    public static final Long DB_ACCESS_TIME = 3000L;

    public static final int PORT = 8080;
    public static final String HOST_NAME = "localhost";
    public static final int BACK_LOG = 50;

    public static final Map<String, Class<?>> servletMappings = new HashMap<>();

    {
        servletMappings.put("/", IndexServlet.class);
        servletMappings.put("/userCount", UserCountServlet.class);
    }
}
