package com.api.twitter.user.application.exception;

public class UserNotExists extends RuntimeException {
    public UserNotExists(String message) {
        super(message);
    }
}
