package com.footalentgroup.exception;

public class ForbiddenException extends RuntimeException {
    private static final String DESCRIPTION = "Forbidden Exception";

  public ForbiddenException() {
  }

  public ForbiddenException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}
