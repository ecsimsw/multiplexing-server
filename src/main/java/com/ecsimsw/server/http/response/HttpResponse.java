package com.ecsimsw.server.http.response;

public class HttpResponse {

    private final String httpVersion;
    private StatusCode statusCode;
    private String body;

    public HttpResponse(String httpVersion) {
        this.httpVersion = httpVersion;
        this.body = "";
    }

    public void ok(String body) {
        this.statusCode = StatusCode.OK;
        this.body = body;
    }

    public void notFound(String body) {
        this.statusCode = StatusCode.NOT_FOUND;
        this.body = body;
    }

    public void badRequest(String body) {
        this.statusCode = StatusCode.BAD_REQUEST;
        this.body = body;
    }

    public void noContent() {
        this.statusCode = StatusCode.NO_CONTENT;
    }

    public String asString() {
        final String firstLine = String.format("%s %s %s", httpVersion, statusCode.getCode(), statusCode.getMessage());
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
