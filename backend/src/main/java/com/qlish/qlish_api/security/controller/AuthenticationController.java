package com.qlish.qlish_api.security.controller;

import com.qlish.qlish_api.security.dto.AuthenticationRequest;
import com.qlish.qlish_api.security.dto.AuthenticationResponse;
import com.qlish.qlish_api.security.dto.RegistrationRequest;
import com.qlish.qlish_api.security.dto.RegistrationResponse;
import com.qlish.qlish_api.exception.CustomQlishException;
import com.qlish.qlish_api.security.service.AuthenticationService;
import com.qlish.qlish_api.security.service.LogoutService;
import com.qlish.qlish_api.util.AppConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RequestMapping(AppConstants.BASE_URL + "/auth")
@RestController
@RequiredArgsConstructor
@Validated
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final LogoutService logoutService;


    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegistrationRequest request){
        RegistrationResponse response = authenticationService.register(request);
        URI newProfileLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(AppConstants.BASE_URL + "/user/{id}")
                .buildAndExpand(response.getUser().getId())
                .toUri();
        return ResponseEntity.created(newProfileLocation).body(authenticationService.register(request));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


    @PostMapping("/refresh")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            authenticationService.refreshAccessToken(request, response);
            response.getOutputStream();
        } catch (IOException e) {
            throw new CustomQlishException("Failed to refresh token: ", e);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logoutService.logout(request, response, authentication);
    }
}

