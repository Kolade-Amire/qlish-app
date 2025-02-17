package com.qlish.qlish_api.user.dto;

import com.qlish.qlish_api.user.model.User;
import lombok.*;

import java.util.List;

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


    public static UserDto fromUser(User user){
        return UserDto.builder()
                .id(user.getId().toHexString())
                .fullName(user.getFirstname() + " " + user.getLastname())
                .email(user.getEmail())
                .profileName(user.getProfileName())
                .allTimePoints(user.getAllTimePoints())
                .profilePictureUrl(user.getProfilePictureUrl())
                .build();
    }

    public static List<UserDto> fromUserList(List<User> users){
        return users.stream().map(
                UserDto::fromUser
        ).toList();
    }
}
