package com.suris.authservice.application;

import com.suris.authservice.api.dto.model.UserDto;
import com.suris.authservice.api.dto.response.UserResponse;
import com.suris.authservice.application.exception.EmailAlreadyExistsException;
import com.suris.authservice.application.mappers.ApplicationUserMapper;
import com.suris.authservice.application.service.UserService;
import com.suris.authservice.domain.model.UserModel;
import com.suris.authservice.infrastructure.adapters.UserRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepositoryAdapter repositoryAdapter;
    @Mock
    private ApplicationUserMapper applicationUserMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_throw_exception_when_email_already_exists() {
        // Arrange
        UserDto userDto = new UserDto("Yahir", "Ortiz", "correo@correo.com", "12345678", "12345678", "USER");
        when(repositoryAdapter.existsByEmail(userDto.email())).thenReturn(true);

        // Act & Assert
        EmailAlreadyExistsException ex = assertThrows(
                EmailAlreadyExistsException.class,
                () -> userService.register(userDto)
        );

        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void register_save_user_when_email_does_not_exist() {
        // Arrange
        UserDto userDto = new UserDto("Sury", "Valerio", "valeriogmonter@gmail.com", "12345678", "12345678", "USER");
        when(repositoryAdapter.existsByEmail(userDto.email())).thenReturn(false);

        UserModel userModel = mock(UserModel.class);
        UserResponse userResponse = mock(UserResponse.class);

        when(applicationUserMapper.toModel(userDto)).thenReturn(userModel);
        when(repositoryAdapter.save(userModel)).thenReturn(userResponse);

        // Act
        UserResponse result = userService.register(userDto);

        // Assert
        assertNotNull(result);
        verify(repositoryAdapter).save(userModel);
    }
}
