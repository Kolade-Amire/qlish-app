package com.qlish.qlish_api.security.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qlish.qlish_api.user.dto.UserAuthenticationDto;
import com.qlish.qlish_api.util.HttpResponse;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private HttpResponse httpResponse;
    @JsonProperty("access_token")
    private String accessToken;
    private UserAuthenticationDto user;
}
