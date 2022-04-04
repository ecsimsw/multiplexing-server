package com.ecsimsw.server.server.http.servlet;

import com.ecsimsw.server.server.http.exception.NotFoundException;
import com.ecsimsw.server.server.http.request.HttpRequest;
import com.ecsimsw.server.server.http.response.HttpResponse;
import com.ecsimsw.server.server.http.response.ResponseFile;

import java.io.IOException;

public class UserCountServlet extends Servlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse httpResponse) {
        try {
            final String indexFilePath = "/user_count.html";
            final ResponseFile file = ResponseFile.of(indexFilePath);
            httpResponse.ok(request.getHttpVersion(), file.asString());
        } catch (IOException e) {
            throw new NotFoundException("no file");
        }
    }
}
