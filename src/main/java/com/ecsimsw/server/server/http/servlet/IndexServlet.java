package com.ecsimsw.server.server.http.servlet;

import com.ecsimsw.server.server.http.request.HttpRequest;
import com.ecsimsw.server.server.http.response.HttpResponse;
import com.ecsimsw.server.server.http.servlet.exception.NotFoundException;
import java.io.IOException;

public class IndexServlet extends Servlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse httpResponse) {
        try {
            final String indexFilePath = "/index.html";
            final ResponseFile file = ResponseFile.of(indexFilePath);
            httpResponse.ok(request.getHttpVersion(), file.asString());
        } catch (IOException e) {
            throw new NotFoundException("no file");
        }
    }
}
