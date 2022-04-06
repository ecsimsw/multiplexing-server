package com.ecsimsw.server.http.servlet;

import com.ecsimsw.server.http.exception.InternalServerException;
import com.ecsimsw.server.http.exception.NotFoundException;
import com.ecsimsw.server.http.request.HttpRequest;
import com.ecsimsw.server.http.response.HttpResponse;
import com.ecsimsw.server.http.response.ResponseFile;

import java.io.IOException;

import static com.ecsimsw.server.config.ServerConfig.BAD_REQUEST_FILE_PATH;
import static com.ecsimsw.server.config.ServerConfig.NOT_FOUND_FILE_PATH;

public class DefaultServlet extends Servlet {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            final String filePath = httpRequest.getPath();
            final ResponseFile file = ResponseFile.of(filePath);
            httpResponse.ok(file.asString());
        } catch (IOException e) {
            throw new NotFoundException("Invalid file");
        }
    }

    public void badRequest(HttpResponse httpResponse) {
        try {
            final ResponseFile file = ResponseFile.of(BAD_REQUEST_FILE_PATH);
            httpResponse.badRequest(file.asString());
        } catch (IOException e) {
            throw new InternalServerException("no file");
        }
    }

    public void notFoundException(HttpResponse httpResponse) {
        try {
            final ResponseFile file = ResponseFile.of(NOT_FOUND_FILE_PATH);
            httpResponse.notFound(file.asString());
        } catch (IOException e) {
            throw new InternalServerException("no file");
        }
    }
}
