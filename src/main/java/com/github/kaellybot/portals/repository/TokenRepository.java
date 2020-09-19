package com.github.kaellybot.portals.repository;

import com.github.kaellybot.portals.model.entity.Token;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


public interface TokenRepository extends ReactiveMongoRepository<Token, String> {

    Mono<Token> findTokenByUsername(String username);
}