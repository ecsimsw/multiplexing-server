package com.ecsimsw.server.http.exception;

public class MethodNotAllowedException extends HttpException {

    public MethodNotAllowedException(String message) {
        super(message);
    }
}
