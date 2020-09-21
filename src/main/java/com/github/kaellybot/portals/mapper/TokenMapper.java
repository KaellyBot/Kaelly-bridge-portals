package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.dto.ExternalTokenDto;
import com.github.kaellybot.portals.model.dto.TokenDto;
import com.github.kaellybot.portals.model.entity.Token;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TokenMapper {

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    protected static final String ALGORITHM_ENCODER = "{bcrypt}";

    public TokenDto map(Token token){
        return TokenDto.builder()
                .id(token.getId())
                .username(token.getUsername())
                .userType(token.getUserType())
                .privileges(token.getPrivileges())
                .build();
    }

    public Token map(ExternalTokenDto token){
        return Token.builder()
                .username(token.getUsername())
                .password(PASSWORD_ENCODER.encode(token.getPassword()))
                .userType(token.getUserType())
                .privileges(token.getPrivileges())
                .build();
    }

    public UserDetails mapUserDetails(Token token){
        return User.builder()
                .username(token.getUsername())
                .password(ALGORITHM_ENCODER + token.getPassword())
                .authorities(Optional.ofNullable(token.getPrivileges()).orElse(Collections.emptyList()).stream()
                        .map(privilege -> new SimpleGrantedAuthority(privilege.name()))
                        .collect(Collectors.toList()))
                .build();
    }
}