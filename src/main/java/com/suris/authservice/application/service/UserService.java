package com.suris.authservice.application.service;

import com.suris.authservice.api.dto.model.UserDto;
import com.suris.authservice.api.dto.model.UserLoginDTO;
import com.suris.authservice.api.dto.response.UserLoginResponse;
import com.suris.authservice.api.dto.response.UserResponse;
import com.suris.authservice.application.exception.EmailAlreadyExistsException;
import com.suris.authservice.application.exception.EmailIsNotRegistered;
import com.suris.authservice.application.exception.PasswordDoesntMatch;
import com.suris.authservice.application.mappers.ApplicationUserMapper;
import com.suris.authservice.domain.model.UserModel;
import com.suris.authservice.infrastructure.adapters.UserRepositoryAdapter;
import com.suris.authservice.infrastructure.security.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserService {

    private final UserRepositoryAdapter repositoryAdapter;
    private final ApplicationUserMapper applicationUserMapper;
    private final JwtProvider jwtProvider;

    public UserService(UserRepositoryAdapter repositoryAdapter, ApplicationUserMapper applicationUserMapper, JwtProvider jwtProvider) {
        this.repositoryAdapter = repositoryAdapter;
        this.applicationUserMapper = applicationUserMapper;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public UserResponse register(UserDto userDto) {
        if (repositoryAdapter.existsByEmail(userDto.email())) {
            throw new EmailAlreadyExistsException("Email " + userDto.email() + " already exists.");
        }

        UserModel userModel = applicationUserMapper.toModel(userDto);
        return repositoryAdapter.save(userModel);
    }

    public UserLoginResponse login(UserLoginDTO userLoginDTO) {
        if (!repositoryAdapter.existsByEmail(userLoginDTO.email())) {
            throw new EmailIsNotRegistered("Email " + userLoginDTO.email() + " is not registered.");
        }

        UserModel user = repositoryAdapter.findByEmail(userLoginDTO.email());

        if (!user.getPassword().matches(userLoginDTO.password())) {
            throw new PasswordDoesntMatch("The password is not correct");
        }

        String token = jwtProvider.generateToken(user.getUsername(), user.getEmail().email(), user.getRole().getValue());
        return new UserLoginResponse(token);
    }
}
