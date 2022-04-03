package com.ecsimsw.server.server.http.response;

public class HttpResponse {

    private String httpVersion;
    private StatusCode statusCode;
    private String body;

    public HttpResponse() {
    }

    public void ok(String httpVersion, String body) {
        this.httpVersion = httpVersion;
        this.statusCode = StatusCode.OK;
        this.body = "<html>\n"
                + "<head><title>404 Not Found</title></head>\n"
                + "<body bgcolor=\"white\">\n"
                + "<center><h1>Hello world</h1></center>\n"
                + "</body>\n"
                + "</html>";
    }

    public void notFound(String httpVersion, String body) {
        this.httpVersion = httpVersion;
        this.statusCode = StatusCode.NOT_FOUND;
        this.body = "<html>\n"
                + "<head><title>404 Not Found</title></head>\n"
                + "<body bgcolor=\"white\">\n"
                + "<center><h1>404 Not Found</h1></center>\n"
                + "</body>\n"
                + "</html>";
    }

    public String asString() {
        final String firstLine = String.format("%s %s %s ", httpVersion, statusCode.getCode(), statusCode.getMessage());
        final String contentTypeLine = "Content-Type: text/html;charset=utf-8 ";
        final String contentLengthLine = String.format("Content-Length: %s ", body.length());
        return String.join("\r\n",
                firstLine,
                contentTypeLine,
                contentLengthLine,
                "",
                body);
    }
}
