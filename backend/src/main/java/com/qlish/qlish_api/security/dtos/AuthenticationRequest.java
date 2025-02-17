package com.qlish.qlish_api.security.dtos;

import com.qlish.qlish_api.validation.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotBlank
    @ValidEmail
    private String email;
    @NotBlank
    private String password;
}
