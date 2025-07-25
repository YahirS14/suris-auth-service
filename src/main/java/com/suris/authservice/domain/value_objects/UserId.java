package com.suris.authservice.domain.value_objects;

import java.io.Serializable;
import java.util.UUID;

public record UserId(UUID value) implements Serializable {

    public UserId {
        if (value == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
    }

    public static UserId newId() {
        return new UserId(UUID.randomUUID());
    }
}
