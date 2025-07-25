package com.suris.authservice.domain.value_objects;

import com.suris.authservice.domain.exception.InvalidPassword;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Password {

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private static final List<Predicate<String>> PASSWORD_VALIDATOR = List.of(
            Objects::nonNull,
            p -> p.length() >= 8,
            p -> p.matches(".*[A-Z].*"),
            p -> p.matches(".*[a-z].*"),
            p -> p.matches(".*\\d.*"),
            p -> p.matches(".*[@#$%^&+=_].*")
    );

    private String hashedPassword;

    public Password(String plainPassword) {
        if (!isValidPassword(plainPassword)) {
            throw new InvalidPassword("Password does not meet complexity requirements.");
        }
        this.hashedPassword = ENCODER.encode(plainPassword);
    }

    public static Password fromHashed(String hashedPassword) {
        Password password = new Password();
        password.hashedPassword = hashedPassword;
        return password;
    }

    private boolean isValidPassword(String plainPassword) {
        return PASSWORD_VALIDATOR.stream().allMatch(rule -> rule.test(plainPassword));
    }

    public boolean matches(String plainPassword) {
        return ENCODER.matches(plainPassword, this.hashedPassword);
    }
}
