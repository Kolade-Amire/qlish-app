package com.qlish.qlish_api.security.dtos;

import com.qlish.qlish_api.user.dto.UserAuthenticationDto;
import com.qlish.qlish_api.util.HttpResponse;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponse {

    private HttpResponse httpResponse;
    private UserAuthenticationDto user;

}
