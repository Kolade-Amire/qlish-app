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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final LogoutService logoutService;


    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegistrationRequest request) {
        RegistrationResponse response = authenticationService.register(request);
        URI newProfileLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getUser().getId())
                .toUri();
        return ResponseEntity.created(newProfileLocation).body(authenticationService.register(request));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(authenticationService.authenticate(request, response));
    }


    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            @RequestBody(required = false) String token, @CookieValue(value = "refreshToken", required = false) String cookieRefreshToken, HttpServletResponse httpServletResponse
    ) {
        String refreshToken = (cookieRefreshToken != null) ? cookieRefreshToken : token;
        if (refreshToken == null) {
            log.error("auth/refresh: No refresh token provided");
            throw new CustomQlishException("Please login again.");
        }
        var response = authenticationService.refreshToken(refreshToken, httpServletResponse);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logoutService.logout(request, response, authentication);
    }
}

