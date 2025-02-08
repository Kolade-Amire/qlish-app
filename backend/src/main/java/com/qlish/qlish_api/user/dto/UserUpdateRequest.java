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
public class UserUpdateRequest {
    private String firstname;
    private String lastname;
    private String profileName;
}
