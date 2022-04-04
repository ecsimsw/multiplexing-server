package com.ecsimsw.server.server.http.servlet;

import com.ecsimsw.server.server.http.request.HttpRequest;
import com.ecsimsw.server.server.http.response.HttpResponse;
import com.ecsimsw.server.server.http.exception.InternalServerException;
import com.ecsimsw.server.server.http.exception.NotFoundException;
import com.ecsimsw.server.server.http.response.ResponseFile;

import java.io.IOException;

public class DefaultServlet extends Servlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse httpResponse) {
        try {
            final String filePath = request.getPath();
            final String httpVersion = request.getHttpVersion();
            final ResponseFile file = ResponseFile.of(filePath);
            httpResponse.ok(httpVersion, file.asString());
        } catch (IOException e) {
            throw new NotFoundException("Invalid file");
        }
    }

    public void badRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            final String httpVersion = httpRequest.getHttpVersion();
            final ResponseFile file = ResponseFile.of("/bad_request.html");
            httpResponse.badRequest(httpVersion, file.asString());
        } catch (IOException e) {
            throw new InternalServerException("no file");
        }
    }

    public void notFoundException(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            final String httpVersion = httpRequest.getHttpVersion();
            final ResponseFile file = ResponseFile.of("/not_found.html");
            httpResponse.notFound(httpVersion, file.asString());
        } catch (IOException e) {
            throw new InternalServerException("no file");
        }
    }
}
