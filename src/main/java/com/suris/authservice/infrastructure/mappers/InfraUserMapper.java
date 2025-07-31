package com.suris.authservice.infrastructure.mappers;

import com.suris.authservice.api.dto.response.UserResponse;
import com.suris.authservice.domain.model.UserModel;
import com.suris.authservice.infrastructure.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InfraUserMapper {

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "email", source = "email.email")
    @Mapping(target = "password", source = "password.hashedPassword")
    UserEntity toEntity(UserModel userModel);

    @Mapping(target = "id", expression = "java(new UserId(entity.getId()))")
    @Mapping(target = "email", expression = "java(new Email(entity.getEmail()))")
    @Mapping(target = "password", expression = "java(Password.fromHashed(entity.getPassword()))")
    UserModel toDomain(UserEntity entity);

    @Mapping(target = "id", expression = "java(userEntity.getId().toString())")
    @Mapping(target = "role", expression = "java(userEntity.getRole().name())")
    @Mapping(target = "createdAt", expression = "java(userEntity.getCreatedAt().toString())")
    @Mapping(target = "updatedAt", expression = "java(userEntity.getUpdatedAt().toString())")
    UserResponse toResponse(UserEntity userEntity);
}
