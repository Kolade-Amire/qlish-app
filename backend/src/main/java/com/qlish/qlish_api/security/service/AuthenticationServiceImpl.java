package com.qlish.qlish_api.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qlish.qlish_api.exception.CustomQlishException;
import com.qlish.qlish_api.exception.EntityAlreadyExistException;
import com.qlish.qlish_api.exception.PasswordsDoNotMatchException;
import com.qlish.qlish_api.security.config.SecurityConstants;
import com.qlish.qlish_api.security.dto.AuthenticationRequest;
import com.qlish.qlish_api.security.dto.AuthenticationResponse;
import com.qlish.qlish_api.security.dto.RegistrationRequest;
import com.qlish.qlish_api.security.dto.RegistrationResponse;
import com.qlish.qlish_api.security.enums.AuthProvider;
import com.qlish.qlish_api.security.enums.Role;
import com.qlish.qlish_api.security.model.Token;
import com.qlish.qlish_api.user.dto.UserAuthenticationDtoMapper;
import com.qlish.qlish_api.user.model.User;
import com.qlish.qlish_api.user.model.UserPrincipal;
import com.qlish.qlish_api.user.service.UserService;
import com.qlish.qlish_api.util.HttpResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.qlish.qlish_api.util.AppConstants.TIME_NOW;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;


    private String doPasswordsMatch(String p1, String p2) {
        if (!p1.equals(p2)) {
            throw new PasswordsDoNotMatchException("Passwords do not match!");
        } else return p2;
    }

    private void validateNewUser(String email) {
        if (userService.userExists(email)) {
            throw new EntityAlreadyExistException(
                    String.format("User with email %s already exists", email)
            );
        }
    }

    @Override
    public RegistrationResponse register(RegistrationRequest request) {

        validateNewUser(request.getEmail());
        var password = doPasswordsMatch(request.getPassword(), request.getConfirmPassword());

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .authProvider(AuthProvider.LOCAL)
                .isBlocked(false)
                .isAccountExpired(false)
                .isEmailVerified(true) //TODO: email verification
                .profileName(request.getProfileName())
                .passwordLastChangedDate(TIME_NOW)
                .build();

        var savedUser = userService.saveUser(user);
        var userDto = UserAuthenticationDtoMapper.mapUserToUserAuthDto(savedUser);


        var httpResponse = HttpResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .httpStatusCode(HttpStatus.CREATED.value())
                .reason(HttpStatus.CREATED.getReasonPhrase())
                .message(SecurityConstants.REGISTERED_MESSAGE)
                .build();

        return RegistrationResponse.builder()
                .httpResponse(httpResponse)
                .user(userDto)
                .build();

    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {


        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            LOGGER.error("Error from authenticationManager.authenticate()", e);
            throw new CustomQlishException("An error occurred while trying to authenticate user. Please try again later.");
        }

        var user = userService.getUserByEmail(request.getEmail());

        user.setLastLoginAt(TIME_NOW);
        //logging in erases user's deletion scheduling
        if (user.getDeletionDate() != null) {
            user.setDeletionDate(null);
        }


        try {
            tokenService.deleteTokenByUserId(user.getId().toString());
        } catch (Exception e) {
            LOGGER.info("existing token has expired and been removed");
        }


        var userPrincipal = new UserPrincipal(user);

        String accessToken;
        String refreshToken;

        try {
            accessToken = jwtService.generateAccessToken(userPrincipal);
            refreshToken = jwtService.generateRefreshToken(userPrincipal);
        } catch (Exception e) {
            LOGGER.error("Error occurred while trying to generate refresh/access token(s)", e);
            throw new CustomQlishException("An error occurred while authenticating user.");
        }

        saveUserRefreshToken(user, refreshToken);

        var response = HttpResponse.builder()
                .timestamp(TIME_NOW)
                .httpStatusCode(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .reason(HttpStatus.OK.getReasonPhrase())
                .message(SecurityConstants.AUTHENTICATED_MESSAGE)
                .build();

        var userDto = UserAuthenticationDtoMapper.mapUserToUserAuthDto(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .httpResponse(response)
                .user(userDto)
                .build();


    }


    private void saveUserRefreshToken(User user, String token) {

        var newTokenEntity = Token.builder()
                .userId(user.getId().toHexString())
                .token(token)
                .tokenType(OAuth2AccessToken.TokenType.BEARER.getValue())
                .isExpired(false)
                .isRevoked(false)
                .build();

        try {
            tokenService.saveToken(newTokenEntity);
        } catch (Exception e) {
            LOGGER.error("Error occurred while saving user token. User Id: {}", user.getId().toString(), e);
        }
    }

    @Override
    public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String accessToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return;
        }

        try {
            accessToken = authHeader.substring(SecurityConstants.TOKEN_PREFIX.length());
            userEmail = jwtService.extractUsername(accessToken);

            if (userEmail != null) {
                var user = this.userService.getUserByEmail(userEmail);
                var userPrincipal = new UserPrincipal(user);
                var refreshToken = tokenService.findTokenByUserId(user.getId().toHexString());

                if (jwtService.isTokenValid(refreshToken.getToken(), userPrincipal)) {
                    var newAccessToken = jwtService.generateAccessToken(userPrincipal);


                    var customHttpResponse = HttpResponse.builder()
                            .httpStatusCode(HttpStatus.OK.value())
                            .httpStatus(HttpStatus.OK)
                            .reason(HttpStatus.OK.getReasonPhrase())
                            .message(SecurityConstants.REFRESHED_MESSAGE)
                            .build();

                    var authResponse = AuthenticationResponse.builder()
                            .httpResponse(customHttpResponse)
                            .accessToken(newAccessToken)
                            .build();

                    objectMapper.writeValue(response.getOutputStream(), authResponse);
                }


            }
        } catch (JwtException e) {
            LOGGER.error("A Jwt Error occurred.", e);
        } catch (Exception e) {
            LOGGER.error("An unexpected error occurred while trying to refreshing token", e);

        }

    }


}

