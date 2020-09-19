package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.dto.ExternalTokenDto;
import com.github.kaellybot.portals.model.dto.TokenDto;
import com.github.kaellybot.portals.model.entity.Token;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TokenMapper {

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

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
}