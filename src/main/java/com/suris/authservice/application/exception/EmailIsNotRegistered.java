package com.suris.authservice.application.exception;

public class EmailIsNotRegistered extends RuntimeException {
    public EmailIsNotRegistered(String message) {
        super(message);
    }
}
