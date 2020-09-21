package com.github.kaellybot.portals.controller;

import com.github.kaellybot.portals.model.constants.UserType;
import com.github.kaellybot.portals.model.dto.ExternalTokenDto;
import com.github.kaellybot.portals.model.entity.Token;
import com.github.kaellybot.portals.repository.TokenRepository;
import com.github.kaellybot.portals.service.TokenService;
import com.github.kaellybot.portals.test.Privilege;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static com.github.kaellybot.portals.controller.PortalConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureWebTestClient
class TokenControllerInternalExceptionTest {

    private static final Token DEFAULT_TOKEN = Token.builder()
            .id("DEFAULT_TOKEN")
            .username("DEFAULT_TOKEN")
            .password("DEFAULT_TOKEN")
            .userType(UserType.USER)
            .privileges(List.of(com.github.kaellybot.portals.model.constants.Privilege.values()))
            .build();

    private static final ExternalTokenDto DEFAULT_EXTERNAL_TOKEN = ExternalTokenDto.builder()
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
    private TokenService tokenService;

    @BeforeEach
    void provideData(){
        tokenRepository.save(DEFAULT_TOKEN).block();
        Mockito.reset(tokenService);
    }

    @Test
    @WithMockUser(authorities = {Privilege.READ_TOKEN})
    void findAllInternalExceptionTest(){
        Mockito.when(tokenService.findAll()).thenThrow(new RuntimeException());
        webTestClient.get()
                .uri(API + TOKEN_FIND_ALL)
                .exchange()
                .expectStatus().isEqualTo(INTERNAL_SERVER_ERROR)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }

    @Test
    @WithMockUser(authorities = {Privilege.SAVE_TOKEN})
    void saveInternalExceptionTest(){
        Mockito.when(tokenService.save(Mockito.any())).thenThrow(new RuntimeException());
        webTestClient.post()
                .uri(API + TOKEN_SAVE)
                .bodyValue(DEFAULT_EXTERNAL_TOKEN)
                .exchange()
                .expectStatus().isEqualTo(INTERNAL_SERVER_ERROR)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }

    @Test
    @WithMockUser(authorities = {Privilege.DELETE_TOKEN})
    void deleteByIdInternalExceptionTest(){
        Mockito.when(tokenService.deleteById(DEFAULT_TOKEN.getId())).thenThrow(new RuntimeException());
        webTestClient.delete()
                .uri(API + TOKEN_DELETE.replace("{" + TOKEN_VAR + "}",  DEFAULT_TOKEN.getId()))
                .exchange()
                .expectStatus().isEqualTo(INTERNAL_SERVER_ERROR)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }
}