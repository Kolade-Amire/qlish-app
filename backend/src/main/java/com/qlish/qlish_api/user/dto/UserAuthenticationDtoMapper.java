package com.qlish.qlish_api.user.dto;

import com.qlish.qlish_api.user.model.User;

public class UserAuthenticationDtoMapper {

    public static UserAuthenticationDto mapUserToUserAuthDto(User user) {
        return UserAuthenticationDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .profileName(user.getProfileName())
                .role(user.getRole())
                .authProvider(user.getAuthProvider().toString())
                .createdAt(user.getCreatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }
}
