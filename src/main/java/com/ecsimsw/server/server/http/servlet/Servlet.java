package com.ecsimsw.server.server.http.servlet;

import com.ecsimsw.server.server.http.HttpMethod;
import com.ecsimsw.server.server.http.HttpRequest;
import com.ecsimsw.server.server.http.HttpResponse;

public abstract class Servlet {

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

    public abstract void doGet(HttpRequest request, HttpResponse httpResponse);

    public abstract void doPost(HttpRequest request, HttpResponse httpResponse);

    public abstract void doPut(HttpRequest request, HttpResponse httpResponse);

    public abstract void doDelete(HttpRequest request, HttpResponse httpResponse);
}
