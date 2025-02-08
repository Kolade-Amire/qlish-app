package com.qlish.qlish_api.security.service;

import com.qlish.qlish_api.security.dto.AuthenticationRequest;
import com.qlish.qlish_api.security.dto.AuthenticationResponse;
import com.qlish.qlish_api.security.dto.RegistrationRequest;
import com.qlish.qlish_api.security.dto.RegistrationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    RegistrationResponse register(RegistrationRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshAccessToken(HttpServletRequest request, HttpServletResponse response) ;
}
