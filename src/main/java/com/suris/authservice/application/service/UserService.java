package com.suris.authservice.application.service;

import com.suris.authservice.api.dto.model.UserDto;
import com.suris.authservice.api.dto.response.UserResponse;
import com.suris.authservice.application.exception.EmailAlreadyExistsException;
import com.suris.authservice.application.mappers.ApplicationUserMapper;
import com.suris.authservice.domain.model.UserModel;
import com.suris.authservice.infrastructure.adapters.UserRepositoryAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserService {

    private final UserRepositoryAdapter repositoryAdapter;
    private final ApplicationUserMapper applicationUserMapper;

    public UserService(UserRepositoryAdapter repositoryAdapter, ApplicationUserMapper applicationUserMapper) {
        this.repositoryAdapter = repositoryAdapter;
        this.applicationUserMapper = applicationUserMapper;
    }

    @Transactional
    public UserResponse register(UserDto userDto) {
        if (repositoryAdapter.existsByEmail(userDto.email())) {
            throw new EmailAlreadyExistsException("Email " + userDto.email() + " already exists.");
        }

        UserModel userModel = applicationUserMapper.toModel(userDto);
        return repositoryAdapter.save(userModel);
    }
}
