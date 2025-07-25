package com.suris.authservice.domain.value_objects;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    Role(String value) {
        this.value = value;
    }
}
