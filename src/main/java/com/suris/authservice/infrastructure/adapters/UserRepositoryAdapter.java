package com.suris.authservice.infrastructure.adapters;

import com.suris.authservice.api.dto.response.UserResponse;
import com.suris.authservice.domain.model.UserModel;
import com.suris.authservice.domain.repository.UserRepository;
import com.suris.authservice.infrastructure.entity.UserEntity;
import com.suris.authservice.infrastructure.mappers.InfraUserMapper;
import com.suris.authservice.infrastructure.repository.UserRepositoryPersistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class UserRepositoryAdapter implements UserRepository {
    private final UserRepositoryPersistence userRepository;
    private final InfraUserMapper userMapper;


    public UserRepositoryAdapter(UserRepositoryPersistence userRepository, InfraUserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse save(UserModel user) {
        UserEntity userEntity = userMapper.toEntity(user);
        log.info("Saving user: {}", userEntity.toString());
        return userMapper.toResponse(userRepository.save(userEntity));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
