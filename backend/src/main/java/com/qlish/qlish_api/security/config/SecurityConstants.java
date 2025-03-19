package com.qlish.qlish_api.security.config;

import com.qlish.qlish_api.util.AppConstants;

public class SecurityConstants {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String AUTHORITIES = "authorities";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED = "You do not have permission to access this page";
    public static final String AUTHENTICATED_MESSAGE = "User authenticated successfully!";
    public static final String REGISTERED_MESSAGE = "User registered successfully!";
    public static final String REFRESHED_MESSAGE = "token refreshed successfully!";

    public static final String PASSWORDS_MISMATCH = "Passwords do not match!";


    public static final String ERROR_URL = "/auth/login?error";

    public static final String[] PUBLIC_URLS = {
            "/auth/**",
            "/oauth2/**",
            "/oauth2/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs.yaml",
            "/swagger-ui/index.html",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",

    };
}
