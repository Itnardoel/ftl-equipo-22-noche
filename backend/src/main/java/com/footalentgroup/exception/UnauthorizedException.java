package com.footalentgroup.exception;

public class UnauthorizedException extends RuntimeException {
    private static final String DESCRIPTION = "Unauthorized Exception";

    public UnauthorizedException() {
    }

    public UnauthorizedException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}
