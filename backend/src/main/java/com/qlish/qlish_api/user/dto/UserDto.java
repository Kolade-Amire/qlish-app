package com.qlish.qlish_api.user.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDto {
    private String id;
    private String email;
    private String fullName;
    private String profilePictureUrl;
    private String profileName;
    private long allTimePoints;
}
