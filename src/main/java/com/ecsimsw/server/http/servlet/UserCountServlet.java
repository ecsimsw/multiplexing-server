package com.ecsimsw.server.http.servlet;

import com.ecsimsw.database.InmemoryDB;
import com.ecsimsw.server.http.exception.BadRequestException;
import com.ecsimsw.server.http.request.HttpRequest;
import com.ecsimsw.server.http.response.HttpResponse;
import com.ecsimsw.server.http.response.ResponseFile;
import com.ecsimsw.server.http.exception.NotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class UserCountServlet extends Servlet {

    private static final String FILE_PATH = "/user_count.html";

    @Override
    public void doGet(HttpRequest request, HttpResponse httpResponse) {
        try {
            InmemoryDB.up();
            final int count = InmemoryDB.select();
            final String resolvedFile = viewResolve(count);
            httpResponse.ok(request.getHttpVersion(), resolvedFile);
        } catch (IOException e) {
            throw new NotFoundException("no file");
        }
    }

    @Override
    public void doPut(HttpRequest request, HttpResponse httpResponse) {
        try {
            final int number = Integer.parseInt(request.getQueryValue("number"));
            InmemoryDB.update(number);
            httpResponse.noContent(request.getHttpVersion());
        } catch (NumberFormatException | NullPointerException e) {
            throw new BadRequestException("Invalid request");
        }
    }

    @Override
    public void doDelete(HttpRequest request, HttpResponse httpResponse) {
        InmemoryDB.delete();
        httpResponse.noContent(request.getHttpVersion());
    }

    private String viewResolve(int count) throws IOException {
        final ResponseFile file = ResponseFile.of(FILE_PATH);
        final Document parse = Jsoup.parse(file.asString());
        final Element number = parse.getElementById("number");
        number.text(String.valueOf(count));
        return parse.html();
    }
}
