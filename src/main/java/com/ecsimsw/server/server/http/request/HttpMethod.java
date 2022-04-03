package com.ecsimsw.server.server.http.request;

import java.util.Arrays;

public enum HttpMethod {
    GET, POST, DELETE, PUT;

    public static HttpMethod of(String method) {
        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(method))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid http method"));
    }
}
