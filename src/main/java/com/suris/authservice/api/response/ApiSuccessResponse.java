package com.suris.authservice.api.response;

import java.time.LocalDateTime;

public record ApiSuccessResponse<T>(
        int status,
        String message,
        T data,
        LocalDateTime timestamp
) {
    public ApiSuccessResponse(int status, String message, T data) {
        this(status, message, data, LocalDateTime.now());
    }
}
