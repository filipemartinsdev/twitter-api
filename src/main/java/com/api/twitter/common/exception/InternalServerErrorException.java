package com.api.twitter.common.exception;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(String message, RuntimeException exception) {
        super(message, exception);
    }
}
