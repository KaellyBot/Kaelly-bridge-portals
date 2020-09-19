package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.model.entity.Token;
import com.github.kaellybot.portals.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TokenService implements ReactiveUserDetailsService {

    private final TokenRepository tokenRepository;

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
        return tokenRepository.findTokenByUsername(username).map(this::map);
    }

    private UserDetails map(Token token){
        return User.builder()
                .username(token.getUsername())
                .password("{bcrypt}" + token.getPassword())
                .authorities(Optional.ofNullable(token.getPrivileges()).orElse(Collections.emptyList()).stream()
                        .map(privilege -> new SimpleGrantedAuthority(privilege.name()))
                        .collect(Collectors.toList()))
                .build();
    }
}
