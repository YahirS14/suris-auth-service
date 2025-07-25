package com.suris.authservice.application.mappers;

import com.suris.authservice.api.dto.model.UserDto;
import com.suris.authservice.domain.model.UserModel;
import com.suris.authservice.domain.value_objects.Email;
import com.suris.authservice.domain.value_objects.Password;
import com.suris.authservice.domain.value_objects.Role;
import com.suris.authservice.domain.value_objects.UserId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;

@Mapper(componentModel = "spring", imports = {
        UserId.class,
        Email.class,
        Password.class,
        Role.class,
        Instant.class
})
public interface ApplicationUserMapper {

    //Dto to Model
    @Mapping(target = "id", expression = "java(UserId.newId())")
    @Mapping(target = "email", source = "email", qualifiedByName = "mapEmail")
    @Mapping(target = "password", source = "password", qualifiedByName = "mapPassword")
    @Mapping(target = "role", source = "role", qualifiedByName = "mapRole")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    UserModel toModel(UserDto dto);

    @Named("mapEmail")
    static Email mapEmail(String email) {
        return new Email(email);
    }

    @Named("mapPassword")
    static Password mapPassword(String plainPassword) {
        return new Password(plainPassword);
    }

    @Named("mapRole")
    static Role mapRole(String role) {
        return Role.valueOf(role.toUpperCase());
    }
}
