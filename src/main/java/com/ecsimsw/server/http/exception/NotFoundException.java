package com.ecsimsw.server.http.exception;

public class NotFoundException extends HttpException {

    public NotFoundException(String message) {
        super(message);
    }
}
