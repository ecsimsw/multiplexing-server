package com.ecsimsw.server.server.http.exception;

public class BadRequestException extends HttpException {

    public BadRequestException(String message) {
        super(message);
    }
}
