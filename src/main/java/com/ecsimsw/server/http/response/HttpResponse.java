package com.ecsimsw.server.http.response;

public class HttpResponse {

    private String httpVersion;
    private StatusCode statusCode;
    private String body;

    public HttpResponse() {
    }

    public void ok(String httpVersion, String body) {
        this.httpVersion = httpVersion;
        this.statusCode = StatusCode.OK;
        this.body = body;
    }

    public void notFound(String httpVersion, String body) {
        this.httpVersion = httpVersion;
        this.statusCode = StatusCode.NOT_FOUND;
        this.body = body;
    }

    public void badRequest(String httpVersion, String body) {
        this.httpVersion = httpVersion;
        this.statusCode = StatusCode.BAD_REQUEST;
        this.body = body;
    }

    public void noContent(String httpVersion) {
        this.httpVersion = httpVersion;
        this.statusCode = StatusCode.NO_CONTENT;
        this.body = "";
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
