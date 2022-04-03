package com.ecsimsw.server.domain.http;

import com.ecsimsw.server.domain.server.MessageHandler;

public class HttpMessageHandler implements MessageHandler {

    @Override
    public String handle(String message) {
        return String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: " + message.getBytes().length + " ",
                "",
                message);
    }
}
