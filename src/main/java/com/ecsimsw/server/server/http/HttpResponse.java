package com.ecsimsw.server.server.http;

public class HttpResponse {

    private String message;
    private String body = "helloWord";

    public String asString() {
        return String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: " + body.getBytes().length + " ",
                "",
                body);
    }

    public String ok() {
        return message = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: " + body.getBytes().length + " ",
                "",
                body);
    }

    public String notFound() {
        return message = "HTTP/1.1 404 Not Found\n"
                + "Server: nginx/0.8.54\n"
                + "Date: Mon, 02 Jan 2012 02:33:17 GMT\n"
                + "Content-Type: text/html\n"
                + "Content-Length: 169\n"
                + "Connection: keep-alive\n"
                + "\n"
                + "<html>\n"
                + "<head><title>404 Not Found</title></head>\n"
                + "<body bgcolor=\"white\">\n"
                + "<center><h1>404 Not Found</h1></center>\n"
                + "<hr><center>nginx/0.8.54</center>\n"
                + "</body>\n"
                + "</html>";
    }
}
