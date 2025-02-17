package com.qlish.qlish_api.security.controllers;

import com.qlish.qlish_api.exceptions.CustomQlishException;
import com.qlish.qlish_api.security.dtos.AuthenticationRequest;
import com.qlish.qlish_api.security.dtos.AuthenticationResponse;
import com.qlish.qlish_api.security.dtos.RegistrationRequest;
import com.qlish.qlish_api.security.dtos.RegistrationResponse;
import com.qlish.qlish_api.security.services.AuthenticationService;
import com.qlish.qlish_api.security.services.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RequestMapping("/auth")
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
                .path("/{id}")
                .buildAndExpand(response.getUser().getId())
                .toUri();
        return ResponseEntity.created(newProfileLocation).body(authenticationService.register(request));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request){
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

