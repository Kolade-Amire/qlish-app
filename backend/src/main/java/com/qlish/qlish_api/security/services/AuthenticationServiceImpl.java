package com.qlish.qlish_api.security.services;

import com.qlish.qlish_api.exceptions.CustomQlishException;
import com.qlish.qlish_api.exceptions.EntityAlreadyExistException;
import com.qlish.qlish_api.exceptions.PasswordsDoNotMatchException;
import com.qlish.qlish_api.security.config.SecurityConstants;
import com.qlish.qlish_api.security.dtos.AuthenticationRequest;
import com.qlish.qlish_api.security.dtos.AuthenticationResponse;
import com.qlish.qlish_api.security.dtos.RegistrationRequest;
import com.qlish.qlish_api.security.dtos.RegistrationResponse;
import com.qlish.qlish_api.security.enums.AuthProvider;
import com.qlish.qlish_api.security.enums.Role;
import com.qlish.qlish_api.security.models.Token;
import com.qlish.qlish_api.user.dto.UserAuthenticationDto;
import com.qlish.qlish_api.user.model.User;
import com.qlish.qlish_api.user.model.UserPrincipal;
import com.qlish.qlish_api.user.services.UserService;
import com.qlish.qlish_api.util.HttpResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

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
        var userDto = UserAuthenticationDto.fromUser(savedUser);


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
    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {


        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            LOGGER.error("Error from authenticationManager.authenticate()", e);
            throw new CustomQlishException("An error occurred while trying to authenticate. Please try again.");
        }

        var user = userService.getUserByEmail(request.getEmail());

        user.setLastLoginAt(TIME_NOW);
        //logging in erases user's deletion scheduling
        if (user.getDeletionDate() != null) {
            user.setDeletionDate(null);
        }
        userService.saveUser(user);


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

        saveToken(user, refreshToken);

        //Set refresh token as HTTP-only cookie for web clients
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/api")
                .maxAge(30 * 24 * 60 * 60)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        var httpResponse = HttpResponse.builder()
                .timestamp(TIME_NOW)
                .httpStatusCode(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .reason(HttpStatus.OK.getReasonPhrase())
                .message(SecurityConstants.AUTHENTICATED_MESSAGE)
                .build();

        var userDto = UserAuthenticationDto.fromUser(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken) //for mobile clients
                .httpResponse(httpResponse)
                .user(userDto)
                .build();


    }


    //save refresh token to redis
    private void saveToken(User user, String token) {

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
    public AuthenticationResponse refreshToken(String refreshToken, HttpServletResponse response) {

        try {
            String email = jwtService.extractUsername(refreshToken);
            User user = userService.getUserByEmail(email);
            UserPrincipal principal = new UserPrincipal(user);
            boolean isValid = jwtService.isTokenValid(refreshToken, principal);
            Token savedRefreshToken = tokenService.findTokenByUserId(user.getId().toHexString());

            if (isValid && tokenService.validateToken(refreshToken, savedRefreshToken)) {
                String newAccessToken = jwtService.generateAccessToken(principal);
                String newRefreshToken = jwtService.generateRefreshToken(principal);

                tokenService.deleteToken(savedRefreshToken);

                saveToken(user, newRefreshToken);

                // Update cookie for web clients
                ResponseCookie newRefreshCookie = ResponseCookie.from("refreshToken", newRefreshToken)
                        .httpOnly(true)
                        .secure(true)
                        .sameSite("Strict")
                        .path("/auth")
                        .maxAge(30 * 24 * 60 * 60) // 30 days
                        .build();
                response.addHeader(HttpHeaders.SET_COOKIE, newRefreshCookie.toString());

                var customHttpResponse = HttpResponse.builder()
                        .httpStatusCode(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .reason(HttpStatus.OK.getReasonPhrase())
                        .message(SecurityConstants.REFRESHED_MESSAGE)
                        .build();

                return AuthenticationResponse.builder()
                        .httpResponse(customHttpResponse)
                        .accessToken(newAccessToken)
                        .refreshToken(newRefreshToken) // for mobile clients
                        .build();
            } else {
                tokenService.deleteToken(savedRefreshToken);
                throw new CustomQlishException("Session expired. Please login again");
            }
        } catch (JwtException e) {
            LOGGER.error("A Jwt Error occurred", e);
            throw new CustomQlishException("Session expired. Please login again");
        } catch (Exception e) {
            LOGGER.error("An unexpected error occurred while trying to refresh token: {}", e.getLocalizedMessage(), e);
            throw new CustomQlishException("Session expired. Please login again");
        }

    }


}

