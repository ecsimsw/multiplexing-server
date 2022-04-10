package com.ecsimsw.server.http.servlet;

import static com.ecsimsw.server.config.ServerConfig.INDEX_FILE_PATH;

import com.ecsimsw.server.http.exception.NotFoundException;
import com.ecsimsw.server.http.request.HttpRequest;
import com.ecsimsw.server.http.response.HttpResponse;
import com.ecsimsw.server.http.response.ResponseFile;
import java.io.IOException;

public class IndexServlet extends Servlet {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            final ResponseFile file = ResponseFile.of(INDEX_FILE_PATH);
            httpResponse.ok(file.asString());
        } catch (IOException e) {
            throw new NotFoundException("no file");
        }
    }
}
