package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.constants.Privilege;
import com.github.kaellybot.portals.model.constants.UserType;
import com.github.kaellybot.portals.model.dto.ExternalTokenDto;
import com.github.kaellybot.portals.model.dto.TokenDto;
import com.github.kaellybot.portals.model.entity.Token;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TokenMapperTest {

    private static final String DEFAULT_ID = "DEFAULT_ID";
    private static final String DEFAULT_USERNAME = "DEFAULT_USERNAME";
    private static final String DEFAULT_PASSWORD = "DEFAULT_PASSWORD";

    @Autowired
    private TokenMapper tokenMapper;

    @ParameterizedTest
    @MethodSource("getTokens")
    void mapServerDtoTest(Token token)
    {
        TokenDto result = tokenMapper.map(token);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull().isEqualTo(token.getId());
        assertThat(result.getUsername()).isNotNull().isEqualTo(token.getUsername());
        assertThat(result.getUserType()).isNotNull().isEqualTo(token.getUserType());
        assertThat(result.getPrivileges()).isNotNull().containsAll(token.getPrivileges());
    }

    @ParameterizedTest
    @MethodSource("getExternalTokens")
    void mapExternalServerDtoTest(ExternalTokenDto token)
    {
        Token result = tokenMapper.map(token);
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isNotNull().isEqualTo(token.getUsername());
        assertThat(result.getPassword()).isNotNull().isNotEqualTo(token.getPassword());
        assertThat(result.getUserType()).isNotNull().isEqualTo(token.getUserType());
        assertThat(result.getPrivileges()).isNotNull().containsAll(token.getPrivileges());
    }

    private static Stream<Token> getTokens() {
        return Stream.of(
                Token.builder()
                        .id(DEFAULT_ID)
                        .username(DEFAULT_USERNAME)
                        .password(DEFAULT_PASSWORD)
                        .userType(UserType.USER)
                        .privileges(Collections.emptyList())
                        .build(),
                Token.builder()
                        .id(DEFAULT_ID)
                        .username(DEFAULT_USERNAME)
                        .password(DEFAULT_PASSWORD)
                        .userType(UserType.USER)
                        .privileges(List.of(Privilege.DELETE_TOKEN, Privilege.READ_TOKEN))
                        .build(),
                Token.builder()
                        .id(DEFAULT_ID)
                        .username(DEFAULT_USERNAME)
                        .password(DEFAULT_PASSWORD)
                        .userType(UserType.USER)
                        .privileges(List.of(Privilege.values()))
                        .build()
                );
    }

    private static Stream<ExternalTokenDto> getExternalTokens() {
        return Stream.of(
                ExternalTokenDto.builder()
                        .username(DEFAULT_USERNAME)
                        .password(DEFAULT_PASSWORD)
                        .userType(UserType.USER)
                        .privileges(Collections.emptyList())
                        .build(),
                ExternalTokenDto.builder()
                        .username(DEFAULT_USERNAME)
                        .password(DEFAULT_PASSWORD)
                        .userType(UserType.USER)
                        .privileges(List.of(Privilege.DELETE_TOKEN, Privilege.READ_TOKEN))
                        .build(),
                ExternalTokenDto.builder()
                        .username(DEFAULT_USERNAME)
                        .password(DEFAULT_PASSWORD)
                        .userType(UserType.USER)
                        .privileges(List.of(Privilege.values()))
                        .build()
        );
    }
}
