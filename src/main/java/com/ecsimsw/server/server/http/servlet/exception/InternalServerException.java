package com.ecsimsw.server.server.http.servlet.exception;

public class InternalServerException extends HttpException {

    public InternalServerException(String message) {
        super(message);
    }
}
