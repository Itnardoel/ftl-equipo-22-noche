package com.footalentgroup.exception;

public class BadRequestException extends RuntimeException {
    private static final String DESCRIPTION = "Bad Request Exception";

    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}
