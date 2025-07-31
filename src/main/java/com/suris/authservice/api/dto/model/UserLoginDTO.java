package com.suris.authservice.api.dto.model;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(
        @NotBlank(message = "User email is required")
        String email,

        @NotBlank(message = "Password is required")
        String password
) {
}
