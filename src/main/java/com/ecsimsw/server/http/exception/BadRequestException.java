package com.ecsimsw.server.http.exception;

public class BadRequestException extends HttpException {

    public BadRequestException(String message) {
        super(message);
    }
}
