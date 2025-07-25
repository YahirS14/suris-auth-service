package com.suris.authservice.api.controller;

import com.suris.authservice.api.dto.model.UserDto;
import com.suris.authservice.api.dto.response.UserResponse;
import com.suris.authservice.api.response.ApiSuccessResponse;
import com.suris.authservice.application.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/v1/users/register")
    public ResponseEntity<ApiSuccessResponse<UserResponse>> registerUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccessResponse<>(201, "User registered successfully successfully", userService.register(userDto)));
    }
}
