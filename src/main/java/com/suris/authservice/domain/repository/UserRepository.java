package com.suris.authservice.domain.repository;

import com.suris.authservice.api.dto.response.UserResponse;
import com.suris.authservice.domain.model.UserModel;

public interface UserRepository {

    UserResponse save(UserModel user);

    boolean existsByEmail(String email);

}