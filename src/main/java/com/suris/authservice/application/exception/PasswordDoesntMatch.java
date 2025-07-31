package com.suris.authservice.application.exception;

public class PasswordDoesntMatch extends RuntimeException {
    public PasswordDoesntMatch(String message) {
        super(message);
    }
}
