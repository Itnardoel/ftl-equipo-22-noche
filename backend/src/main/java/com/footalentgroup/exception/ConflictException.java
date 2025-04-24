package com.footalentgroup.exception;

public class ConflictException extends RuntimeException {
    private static final String DESCRIPTION = "Conflict Exception";

    public ConflictException() {
    }

    public ConflictException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}
