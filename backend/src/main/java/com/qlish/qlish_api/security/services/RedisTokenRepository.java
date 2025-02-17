package com.qlish.qlish_api.security.services;

import com.qlish.qlish_api.security.models.Token;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RedisTokenRepository extends ListCrudRepository<Token, Integer> {

    Optional<Token> findByToken(String token);

    Optional<Token> findByUserId(String userId);

}
