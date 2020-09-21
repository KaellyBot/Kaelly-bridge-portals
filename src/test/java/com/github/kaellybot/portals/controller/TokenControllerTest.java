package com.github.kaellybot.portals.controller;

import com.github.kaellybot.portals.mapper.TokenMapper;
import com.github.kaellybot.portals.model.constants.UserType;
import com.github.kaellybot.portals.model.dto.ExternalTokenDto;
import com.github.kaellybot.portals.model.dto.TokenDto;
import com.github.kaellybot.portals.model.entity.Token;
import com.github.kaellybot.portals.repository.TokenRepository;
import com.github.kaellybot.portals.test.Privilege;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.kaellybot.portals.controller.PortalConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureWebTestClient
class TokenControllerTest {

    private static final Token DEFAULT_TOKEN = Token.builder()
            .id("DEFAULT_TOKEN")
            .username("DEFAULT_TOKEN")
            .password("DEFAULT_TOKEN")
            .userType(UserType.USER)
            .privileges(List.of(com.github.kaellybot.portals.model.constants.Privilege.values()))
            .build();

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private TokenMapper tokenMapper;

    @BeforeEach
    void provideData(){
        tokenRepository.deleteAll()
                .thenMany(tokenRepository.saveAll(getTokens().collect(Collectors.toList())))
                .then(tokenRepository.save(DEFAULT_TOKEN))
                .block();
    }

    @ParameterizedTest
    @WithMockUser(authorities = {Privilege.READ_TOKEN})
    @MethodSource("getTokens")
    void findAllTest(Token token){
        webTestClient.get()
                .uri(API + TOKEN_FIND_ALL)
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBodyList(TokenDto.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(tokenMapper.map(token)));
    }

    @Test
    void findAllAuthenticationTest(){
        webTestClient.get()
                .uri(API + TOKEN_FIND_ALL)
                .exchange()
                .expectStatus().isEqualTo(UNAUTHORIZED);
    }

    @Test
    @WithMockUser
    void findAllAuthorizationTest(){
        webTestClient.get()
                .uri(API + TOKEN_FIND_ALL)
                .exchange()
                .expectStatus().isEqualTo(FORBIDDEN);
    }

    @ParameterizedTest
    @WithMockUser(authorities = {Privilege.SAVE_TOKEN})
    @MethodSource("getExternalTokens")
    void saveTest(ExternalTokenDto token){
        webTestClient.post()
                .uri(API + TOKEN_SAVE)
                .bodyValue(token)
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(TokenDto.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .satisfies(savedToken -> {
                            assertThat(savedToken.getUsername()).isEqualTo(token.getUsername());
                            assertThat(savedToken.getUserType()).isEqualTo(token.getUserType());
                            assertThat(savedToken.getPrivileges()).isEqualTo(token.getPrivileges());
                        }));
    }

    @Test
    @WithMockUser(authorities = {Privilege.SAVE_TOKEN})
    void saveMissingUsernameTest(){
        final ExternalTokenDto SAVED_TOKEN = ExternalTokenDto.builder()
                .password(DEFAULT_TOKEN.getPassword())
                .userType(UserType.USER)
                .privileges(List.of(com.github.kaellybot.portals.model.constants.Privilege.values()))
                .build();

        webTestClient.post()
                .uri(API + TOKEN_SAVE)
                .bodyValue(SAVED_TOKEN)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(USERNAME_NOT_FOUND_MESSAGE));
    }

    @Test
    @WithMockUser(authorities = {Privilege.SAVE_TOKEN})
    void saveMissingPasswordTest(){
        final ExternalTokenDto SAVED_TOKEN = ExternalTokenDto.builder()
                .username("saveMissingPasswordTest")
                .userType(UserType.USER)
                .privileges(List.of(com.github.kaellybot.portals.model.constants.Privilege.values()))
                .build();

        webTestClient.post()
                .uri(API + TOKEN_SAVE)
                .bodyValue(SAVED_TOKEN)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(PASSWORD_NOT_FOUND_MESSAGE));
    }

    @Test
    @WithMockUser(authorities = {Privilege.SAVE_TOKEN})
    void saveMissingUserTypeTest(){
        final ExternalTokenDto SAVED_TOKEN = ExternalTokenDto.builder()
                .username("saveMissingUserTypeTest")
                .password("saveMissingUserTypeTest")
                .userType(UserType.USER)
                .privileges(Collections.emptyList())
                .build();

        webTestClient.post()
                .uri(API + TOKEN_SAVE)
                .bodyValue(SAVED_TOKEN)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(PRIVILEGE_NOT_FOUND_MESSAGE));
    }

    @Test
    @WithMockUser(authorities = {Privilege.SAVE_TOKEN})
    void saveMissingPrivilegeTest(){
        final ExternalTokenDto SAVED_TOKEN = ExternalTokenDto.builder()
                .username("saveMissingPrivilegeTest")
                .password("saveMissingPrivilegeTest")
                .privileges(List.of(com.github.kaellybot.portals.model.constants.Privilege.values()))
                .build();

        webTestClient.post()
                .uri(API + TOKEN_SAVE)
                .bodyValue(SAVED_TOKEN)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(USER_TYPE_NOT_FOUND_MESSAGE));
    }

    @Test
    @WithMockUser(authorities = {Privilege.SAVE_TOKEN})
    void saveTokenAlreadyExistsTest(){
        final ExternalTokenDto SAVED_TOKEN = ExternalTokenDto.builder()
                .username(DEFAULT_TOKEN.getUsername())
                .password(DEFAULT_TOKEN.getPassword())
                .userType(UserType.USER)
                .privileges(List.of(com.github.kaellybot.portals.model.constants.Privilege.values()))
                .build();

        webTestClient.post()
                .uri(API + TOKEN_SAVE)
                .bodyValue(SAVED_TOKEN)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(TOKEN_ALREADY_EXISTS_MESSAGE));
    }

    @Test
    void saveAuthenticationTest(){
        webTestClient.post()
                .uri(API + TOKEN_SAVE)
                .exchange()
                .expectStatus().isEqualTo(UNAUTHORIZED);
    }

    @Test
    @WithMockUser
    void saveAuthorizationTest(){
        webTestClient.post()
                .uri(API + TOKEN_SAVE)
                .exchange()
                .expectStatus().isEqualTo(FORBIDDEN);
    }

    @Test
    @WithMockUser(authorities = {Privilege.DELETE_TOKEN})
    void deleteByIdTest(){
        webTestClient.delete()
                .uri(API + TOKEN_DELETE.replace("{" + TOKEN_VAR + "}",  DEFAULT_TOKEN.getId()))
                .exchange()
                .expectStatus().isEqualTo(OK);
        StepVerifier.create(tokenRepository.findById(DEFAULT_TOKEN.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    void deleteByIdAuthenticationTest(){
        webTestClient.delete()
                .uri(API + TOKEN_DELETE.replace("{" + TOKEN_VAR + "}",  DEFAULT_TOKEN.getId()))
                .exchange()
                .expectStatus().isEqualTo(UNAUTHORIZED);
    }

    @Test
    @WithMockUser
    void deleteByIdAuthorizationTest(){
        webTestClient.delete()
                .uri(API + TOKEN_DELETE.replace("{" + TOKEN_VAR + "}",  DEFAULT_TOKEN.getId()))
                .exchange()
                .expectStatus().isEqualTo(FORBIDDEN);
    }

    private static Stream<Token> getTokens() {
        return Stream.of(
                Token.builder()
                    .id("1")
                    .username("1")
                    .password("1")
                    .userType(UserType.USER)
                    .privileges(Collections.emptyList())
                    .build(),
                Token.builder()
                    .id("2")
                    .username("2")
                    .password("2")
                    .userType(UserType.APPLICATION)
                    .privileges(List.of(com.github.kaellybot.portals.model.constants.Privilege.values()))
                    .build()
        );
    }

    private static Stream<ExternalTokenDto> getExternalTokens() {
        return Stream.of(
                ExternalTokenDto.builder()
                        .username("3")
                        .password("3")
                        .userType(UserType.USER)
                        .privileges(List.of(com.github.kaellybot.portals.model.constants.Privilege.READ_DIMENSION))
                        .build(),
                ExternalTokenDto.builder()
                        .username("4")
                        .password("4")
                        .userType(UserType.APPLICATION)
                        .privileges(List.of(com.github.kaellybot.portals.model.constants.Privilege.values()))
                        .build()
        );
    }
}