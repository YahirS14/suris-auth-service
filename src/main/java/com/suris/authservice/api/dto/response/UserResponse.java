package com.suris.authservice.api.dto.response;

public record UserResponse(
        String id,
        String username,
        String lastName,
        String email,
        String phoneNumber,
        String role,
        String createdAt,
        String updatedAt
) {
}
