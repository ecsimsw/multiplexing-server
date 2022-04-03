package com.ecsimsw.server.server.http.request;

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
            this.httpVersion = firstLineParams[2];
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("[ERROR] : invalid http request format error");
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

/* Request sample
GET /favicon.ico HTTP/1.1
Host: localhost:8080
Connection: keep-alive
sec-ch-ua: " Not A;Brand";v="99", "Chromium";v="100", "Google Chrome";v="100"
sec-ch-ua-mobile: ?0
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.60 Safari/537.36
sec-ch-ua-platform: "macOS"
Accept: image/avif,image/webp,image/apng,image/svg+xml,image/*,*\/*;q=0.8
Sec-Fetch-Site: same-origin
Sec-Fetch-Mode: no-cors
Sec-Fetch-Dest: image
Referer: http://localhost:8080/
Accept-Encoding: gzip, deflate, br
Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7
Cookie: Idea-e24adac9=53f908c0-3c18-4e6e-bf5e-dc3a20e14137
 */
