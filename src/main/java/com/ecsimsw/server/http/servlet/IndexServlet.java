package com.ecsimsw.server.http.servlet;

import com.ecsimsw.server.http.exception.NotFoundException;
import com.ecsimsw.server.http.request.HttpRequest;
import com.ecsimsw.server.http.response.HttpResponse;
import com.ecsimsw.server.http.response.ResponseFile;

import java.io.IOException;

import static com.ecsimsw.server.ServerConfig.INDEX_FILE_PATH;

public class IndexServlet extends Servlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse httpResponse) {
        try {
            final String indexFilePath = INDEX_FILE_PATH;
            final ResponseFile file = ResponseFile.of(indexFilePath);
            httpResponse.ok(request.getHttpVersion(), file.asString());
        } catch (IOException e) {
            throw new NotFoundException("no file");
        }
    }
}
