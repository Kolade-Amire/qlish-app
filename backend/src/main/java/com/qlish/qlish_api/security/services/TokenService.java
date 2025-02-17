package com.qlish.qlish_api.security.services;

import com.qlish.qlish_api.security.models.Token;
import com.qlish.qlish_api.exceptions.CustomQlishException;
import com.qlish.qlish_api.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);
    private final RedisTokenRepository tokenRepository;


    public Token findTokenByUserId(String userId) {
        return tokenRepository.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException(String.format("Could not get token for user with id: %s ", userId)));
    }

    public Token findByToken(String token) {
        return tokenRepository.findByToken(token).orElseThrow(() -> new EntityNotFoundException("token not found"));
    }

    public void saveToken(Token token) {
        try {
            tokenRepository.save(token);
        } catch (Exception e) {
            throw new CustomQlishException("Failed to save token", e);
        }
    }

    public void deleteTokenByUserId(String userId) {
        try {
            var token = findTokenByUserId(userId);
            deleteToken(token);
        } catch (Exception e) {
            LOGGER.error("failed to delete token for user, {}", userId);
        }
    }

    public void deleteToken(Token token){
        try {
            tokenRepository.delete(token);
        } catch (Exception e) {
            LOGGER.error("token not found, failed to delete token");
        }
    }

}
