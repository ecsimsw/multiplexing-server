package com.ecsimsw.server.http.servlet;

import com.ecsimsw.server.database.InmemoryDB;
import com.ecsimsw.server.http.exception.BadRequestException;
import com.ecsimsw.server.http.exception.NotFoundException;
import com.ecsimsw.server.http.request.HttpRequest;
import com.ecsimsw.server.http.response.HttpResponse;
import com.ecsimsw.server.http.response.ResponseFile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import static com.ecsimsw.server.ServerConfig.USER_COUNT_FILE_PATH;

public class UserCountServlet extends Servlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse httpResponse) {
        try {
            InmemoryDB.up();
            final int count = InmemoryDB.select();
            final String resolvedFile = viewResolve(count);
            httpResponse.ok(request.getHttpVersion(), resolvedFile);
        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
            throw new BadRequestException("Invalid request");
        }
    }

    @Override
    public void doDelete(HttpRequest request, HttpResponse httpResponse) {
        InmemoryDB.delete();
        httpResponse.noContent(request.getHttpVersion());
    }

    private String viewResolve(int count) throws IOException {
        final ResponseFile file = ResponseFile.of(USER_COUNT_FILE_PATH);
        final Document parse = Jsoup.parse(file.asString());
        final Element number = parse.getElementById("number");
        number.text(String.valueOf(count));
        return parse.html();
    }
}
