package com.ecsimsw.server.http.servlet;

import static com.ecsimsw.server.config.ServerConfig.USER_COUNT_FILE_PATH;

import com.ecsimsw.server.database.InmemoryDB;
import com.ecsimsw.server.http.exception.BadRequestException;
import com.ecsimsw.server.http.exception.NotFoundException;
import com.ecsimsw.server.http.request.HttpRequest;
import com.ecsimsw.server.http.response.HttpResponse;
import com.ecsimsw.server.http.response.ResponseFile;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class UserCountServlet extends Servlet {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            final int number = InmemoryDB.select();
            InmemoryDB.update(number + 1);
            httpResponse.ok(viewResolve(number));
        } catch (IOException e) {
            e.printStackTrace();
            throw new NotFoundException("No file");
        }
    }

    @Override
    public void doPut(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            final int number = Integer.parseInt(httpRequest.getQueryValue("number"));
            InmemoryDB.update(number);
            httpResponse.noContent();
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            throw new BadRequestException("Invalid request");
        }
    }

    @Override
    public void doDelete(HttpRequest httpRequest, HttpResponse httpResponse) {
        InmemoryDB.delete();
        httpResponse.noContent();
    }

    private String viewResolve(int count) throws IOException {
        final ResponseFile file = ResponseFile.of(USER_COUNT_FILE_PATH);
        final Document parse = Jsoup.parse(file.asString());
        final Element number = parse.getElementById("number");
        number.text(String.valueOf(count));
        return parse.html();
    }
}
