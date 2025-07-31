package com.suris.authservice.application;

import com.suris.authservice.api.dto.model.UserDto;
import com.suris.authservice.api.dto.model.UserLoginDTO;
import com.suris.authservice.api.dto.response.UserLoginResponse;
import com.suris.authservice.api.dto.response.UserResponse;
import com.suris.authservice.application.exception.EmailAlreadyExistsException;
import com.suris.authservice.application.exception.EmailIsNotRegistered;
import com.suris.authservice.application.exception.PasswordDoesntMatch;
import com.suris.authservice.application.mappers.ApplicationUserMapper;
import com.suris.authservice.application.service.UserService;
import com.suris.authservice.domain.model.UserModel;
import com.suris.authservice.domain.value_objects.Email;
import com.suris.authservice.domain.value_objects.Password;
import com.suris.authservice.domain.value_objects.Role;
import com.suris.authservice.infrastructure.adapters.UserRepositoryAdapter;
import com.suris.authservice.infrastructure.security.JwtProvider;
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
    @Mock
    private JwtProvider jwtProvider;

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
        EmailAlreadyExistsException ex = assertThrows(EmailAlreadyExistsException.class, () -> userService.register(userDto));

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

    @Test
    void login_should_return_token_when_credentials_are_valid() {
        //Arrage
        UserLoginDTO loginDTO = new UserLoginDTO("correo@correo.com", "Te_amo140218");
        UserModel userModel = mock(UserModel.class);

        //Mock Value Objects
        Email email = new Email("correo@correo.com");
        Password password = new Password("Te_amo140218");

        when(repositoryAdapter.existsByEmail(loginDTO.email())).thenReturn(true);
        when(repositoryAdapter.findByEmail(loginDTO.email())).thenReturn(userModel);
        when(userModel.getPassword()).thenReturn(password);
        when(userModel.getUsername()).thenReturn("Yahir");
        when(userModel.getEmail()).thenReturn(email);
        when(userModel.getRole()).thenReturn(Role.USER);
        when(jwtProvider.generateToken("Yahir", "correo@correo.com", "USER")).thenReturn("jwt-token");

        UserLoginResponse response = userService.login(loginDTO);

        //Assert
        assertNotNull(response);
        assertEquals("jwt-token", response.token());
    }

    @Test
    void login_should_throw_exception_when_email_is_not_registered() {
        // Arrange
        UserLoginDTO loginDTO = new UserLoginDTO("pepe@pepe.com", "12345678");

        when(repositoryAdapter.existsByEmail(loginDTO.email())).thenReturn(false);

        // Act & Assert
        EmailIsNotRegistered ex = assertThrows(EmailIsNotRegistered.class, () -> userService.login(loginDTO));

        assertTrue(ex.getMessage().contains("is not registered"));
    }

    @Test
    void login_should_throw_exception_when_password_does_not_match() {
        // Arrange
        UserLoginDTO loginDTO = new UserLoginDTO("pepe@pepe.com", "12345678");
        UserModel userModel = mock(UserModel.class);
        Password password = mock(Password.class);

        when(repositoryAdapter.existsByEmail(loginDTO.email())).thenReturn(true);
        when(repositoryAdapter.findByEmail(loginDTO.email())).thenReturn(userModel);
        when(userModel.getPassword()).thenReturn(password);
        when(password.matches(loginDTO.password())).thenReturn(false);

        PasswordDoesntMatch ex = assertThrows(PasswordDoesntMatch.class, () -> userService.login(loginDTO));

        assertTrue(ex.getMessage().contains("The password is not correct"));
    }
}
