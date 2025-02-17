package com.qlish.qlish_api.security.services;

import com.qlish.qlish_api.security.dtos.AuthenticationRequest;
import com.qlish.qlish_api.security.dtos.AuthenticationResponse;
import com.qlish.qlish_api.security.dtos.RegistrationRequest;
import com.qlish.qlish_api.security.dtos.RegistrationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    RegistrationResponse register(RegistrationRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshAccessToken(HttpServletRequest request, HttpServletResponse response) ;
}
