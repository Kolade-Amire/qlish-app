package com.qlish.qlish_api.user.dto;

import com.qlish.qlish_api.security.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class UserAuthenticationDto {

    private ObjectId id;
    private String email;
    private String profileName;
    private Role role;
    private String authProvider;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
}