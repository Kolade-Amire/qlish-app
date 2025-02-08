package com.qlish.qlish_api.security.dto;

import com.qlish.qlish_api.user.dto.UserAuthenticationDto;
import com.qlish.qlish_api.util.HttpResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class RegistrationResponse {

    private HttpResponse httpResponse;
    private UserAuthenticationDto user;

}
