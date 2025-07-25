package com.suris.authservice.domain.value_objects;

import com.suris.authservice.domain.exception.InvalidEmail;

import java.util.regex.Pattern;

public record Email(String email) {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    public Email {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEmail("Invalid email format: " + email);
        }
    }
}
