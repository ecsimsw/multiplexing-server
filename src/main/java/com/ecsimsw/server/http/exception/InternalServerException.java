package com.ecsimsw.server.http.exception;

public class InternalServerException extends HttpException {

    public InternalServerException(String message) {
        super(message);
    }
}
