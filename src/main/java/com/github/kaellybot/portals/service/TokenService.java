package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.mapper.TokenMapper;
import com.github.kaellybot.portals.model.entity.Token;
import com.github.kaellybot.portals.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class TokenService implements ReactiveUserDetailsService {

    private final TokenRepository tokenRepository;

    private final TokenMapper tokenMapper;

    public Mono<Token> save(Token token){
        return tokenRepository.save(token);
    }

    public Flux<Token> findAll(){
        return tokenRepository.findAll();
    }

    public Mono<Void> deleteById(String id){
        return tokenRepository.deleteById(id);
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return tokenRepository.findTokenByUsername(username).map(tokenMapper::mapUserDetails);
    }
}