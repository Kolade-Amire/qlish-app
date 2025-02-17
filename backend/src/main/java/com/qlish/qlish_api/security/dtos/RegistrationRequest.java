package com.qlish.qlish_api.security.dtos;

import com.qlish.qlish_api.validation.StrongPassword;
import com.qlish.qlish_api.validation.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RegistrationRequest {
    @NotBlank(message = "email is required")
    @ValidEmail()
    private String email;

    @NotBlank(message = "password is required")
    @StrongPassword(message = "password must be at least 8 characters long, include an uppercase letter, a number, and a special character")
    private String password;

    @NotBlank(message = "you have to confirm password")
    @StrongPassword
    private String confirmPassword;

    @NotBlank(message = "a profile username is required")
    private String profileName;
}
