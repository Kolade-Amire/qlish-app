package com.qlish.qlish_api.security.service;

import com.qlish.qlish_api.exception.CustomQlishException;
import com.qlish.qlish_api.exception.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.WeakKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Value("${jwt.access-token-expiration}")
    private long ACCESS_TOKEN_EXPIRATION;

    @Value("${jwt.refresh-token-expiration}")
    private long REFRESH_TOKEN_EXPIRATION;

    @Value("${jwt.issuer}")
    private String JWT_ISSUER;

    @Value("${jwt.audience}")
    private String JWT_AUDIENCE;

    //Extract any specific type of claim from token
    public <T> T extractAnyClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        try {
            return claimsResolver.apply(claims);
        } catch (Exception e) {
            throw new CustomQlishException("Error applying claims resolver to get claim from Jwt.");
        }
    }

    private Claims extractAllClaims(String token) {


        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new CustomQlishException("Jwt is expired.");
        } catch (UnsupportedJwtException e) {
            throw new CustomQlishException("Unsupported Jwt format.");
        } catch (MalformedJwtException e) {
            throw new CustomQlishException("Jwt was not correctly constructed and it is being rejected.");
        } catch (SignatureException e) {
            throw new CustomQlishException("Unrecognized Jwt signature.");
        } catch (IllegalArgumentException e) {
            throw new CustomQlishException("An illegal argument was passed so claims can't be extracted from Jwt as expected.");
        }
    }

    private Key getSignKey() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (DecodingException e) {
            throw new CustomQlishException("Error decoding secret. Hence, couldn't get Jwt sign key.");
        } catch (WeakKeyException e) {
            throw new CustomQlishException("A weak key exception occurred while trying to get sign key for jwt.");
        }
    }

    //calls the generics method to extract specific claim(username)
    public String extractUsername(String token) {
        return extractAnyClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractAnyClaim(token, Claims::getExpiration);
    }

    private String buildToken(Map<String, Object> claims, UserDetails userDetails, long expiration) {
        try {
            return Jwts
                    .builder()
                    .setClaims(claims)
                    .setSubject(userDetails.getUsername())
                    .setIssuer(JWT_ISSUER)
                    .setAudience(JWT_AUDIENCE)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(getTimeNowInMilliSeconds() + expiration))
                    .signWith(getSignKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (InvalidKeyException e) {
            throw new CustomQlishException("Invalid JWT sign key.");
        } catch (Exception e) {
            LOGGER.error("An error occurred:", e);
            throw new CustomQlishException("An unexpected error occurred while building jwt.");
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token)
                    .before(new Date(getTimeNowInMilliSeconds()));
        } catch (Exception e) {
            LOGGER.error("error extracting expiration from token");
            return true;
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        if (userDetails == null) {
            throw new TokenException("Null user principal. Unable to validate token. ");
        }

        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateAccessToken(UserDetails userDetails) {
        if (userDetails == null) {
            throw new TokenException("Null user principal. Unable to generate access token. ");
        }

        var claims = createUserClaims(userDetails);
        return buildToken(claims, userDetails, ACCESS_TOKEN_EXPIRATION);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        var claims = createUserClaims(userDetails);
        return buildToken(claims, userDetails, REFRESH_TOKEN_EXPIRATION);
    }

    private long getTimeNowInMilliSeconds() {
        return Instant.now(Clock.systemUTC()).toEpochMilli();
    }

    public Map<String, Object> createUserClaims(UserDetails userDetails) {
        if (userDetails.getAuthorities().isEmpty()){
            LOGGER.error("User has empty authorities list.");
            throw new CustomQlishException("invalid user authorities");
        }

        try {
            var claims = new HashMap<String, Object>();
            List<String> authorities = userDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            claims.put("authorities", authorities);
            claims.put("username", userDetails.getUsername());
            return claims;
        } catch (Exception e) {
            LOGGER.error("An unexpected error occurred: ", e);
            throw new CustomQlishException("An unexpected error occurred while trying to create jwt claims from user principal.");
        }
    }

}
