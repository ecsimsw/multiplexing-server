package com.ecsimsw.server.server.http.servlet;

import com.ecsimsw.server.server.http.request.HttpMethod;
import com.ecsimsw.server.server.http.request.HttpRequest;
import com.ecsimsw.server.server.http.response.HttpResponse;

public abstract class Servlet {

    protected Servlet() {
    }

    public void doService(HttpRequest request, HttpResponse httpResponse) {
        if(request.getMethod() == HttpMethod.GET) {
            doGet(request, httpResponse);
        }

        if(request.getMethod() == HttpMethod.POST) {
            doPost(request, httpResponse);
        }

        if(request.getMethod() == HttpMethod.PUT) {
            doPut(request, httpResponse);
        }

        if(request.getMethod() == HttpMethod.DELETE) {
            doDelete(request, httpResponse);
        }
    }

    public void doGet(HttpRequest request, HttpResponse httpResponse) {
        throw new IllegalArgumentException("method not allowed");
    }

    public void doPost(HttpRequest request, HttpResponse httpResponse) {
        throw new IllegalArgumentException("method not allowed");
    }

    public void doPut(HttpRequest request, HttpResponse httpResponse) {
        throw new IllegalArgumentException("method not allowed");
    }

    public void doDelete(HttpRequest request, HttpResponse httpResponse) {
        throw new IllegalArgumentException("method not allowed");
    }
}
