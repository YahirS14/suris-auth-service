package com.suris.authservice.domain.model;

import com.suris.authservice.domain.value_objects.Email;
import com.suris.authservice.domain.value_objects.Password;
import com.suris.authservice.domain.value_objects.Role;
import com.suris.authservice.domain.value_objects.UserId;
import lombok.*;

import java.time.Instant;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class UserModel {

    @EqualsAndHashCode.Include
    private final UserId id;
    private String username;
    private String lastName;
    private Email email;
    private String phoneNumber;
    private Password password;
    private Role role;
    private final Instant createdAt;
    private Instant updatedAt;

}
