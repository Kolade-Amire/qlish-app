package com.qlish.qlish_api.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UserViewResponse {
    private String profilePictureUrl;
    private String profileName;
    private long allTimePoints;
}
