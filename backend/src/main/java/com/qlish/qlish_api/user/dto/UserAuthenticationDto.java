package com.qlish.qlish_api.user.dto;

import com.qlish.qlish_api.security.enums.Role;
import com.qlish.qlish_api.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class UserAuthenticationDto {
    private String id;
    private String email;
    private String profileName;
    private Role role;
    private String authProvider;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;


    public static UserAuthenticationDto fromUser(User user) {
        return UserAuthenticationDto.builder()
                .id(user.getId().toHexString())
                .email(user.getEmail())
                .profileName(user.getProfileName())
                .role(user.getRole())
                .authProvider(user.getAuthProvider().toString())
                .createdAt(user.getCreatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }
}