package com.ecsimsw.server.http.request;

public class HttpRequest {

    private final HttpMethod method;
    private final UrlPath urlPath;
    private final String httpVersion;

    public HttpRequest(String message) {
        try {
            final String firstLine = message.split("\n")[0];
            final String[] firstLineParams = firstLine.split(" ");

            this.method = HttpMethod.of(firstLineParams[0]);
            this.urlPath = UrlPath.of(firstLineParams[1]);
            this.httpVersion = firstLineParams[2].split("\r")[0];
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("[ERROR] : invalid http request format error ");
        }
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return urlPath.getPath();
    }

    public String getQueryValue(String key) {
        return urlPath.getQueryValue(key);
    }

    public String getHttpVersion() {
        return httpVersion;
    }
}
