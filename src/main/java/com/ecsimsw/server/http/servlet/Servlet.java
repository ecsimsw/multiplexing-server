package com.ecsimsw.server.http.servlet;

import com.ecsimsw.server.http.exception.MethodNotAllowedException;
import com.ecsimsw.server.http.request.HttpMethod;
import com.ecsimsw.server.http.request.HttpRequest;
import com.ecsimsw.server.http.response.HttpResponse;

public abstract class Servlet {

    protected Servlet() {
    }

    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        if(httpRequest.getMethod() == HttpMethod.GET) {
            doGet(httpRequest, httpResponse);
        }

        if(httpRequest.getMethod() == HttpMethod.POST) {
            doPost(httpRequest, httpResponse);
        }

        if(httpRequest.getMethod() == HttpMethod.PUT) {
            doPut(httpRequest, httpResponse);
        }

        if(httpRequest.getMethod() == HttpMethod.DELETE) {
            doDelete(httpRequest, httpResponse);
        }
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new MethodNotAllowedException("method not allowed");
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new MethodNotAllowedException("method not allowed");
    }

    public void doPut(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new MethodNotAllowedException("method not allowed");
    }

    public void doDelete(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new MethodNotAllowedException("method not allowed");
    }
}
