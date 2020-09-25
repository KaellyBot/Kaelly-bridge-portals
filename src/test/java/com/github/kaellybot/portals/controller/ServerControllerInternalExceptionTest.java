package com.github.kaellybot.portals.controller;

import com.github.kaellybot.commons.model.constants.Game;
import com.github.kaellybot.commons.model.constants.Language;
import com.github.kaellybot.commons.model.entity.Server;
import com.github.kaellybot.commons.repository.ServerRepository;
import com.github.kaellybot.commons.service.ServerService;
import com.github.kaellybot.portals.model.dto.ExternalServerDto;
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

import java.util.Map;

import static com.github.kaellybot.portals.controller.PortalConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureWebTestClient
class ServerControllerInternalExceptionTest {

    private static final Server DEFAULT_SERVER = Server.builder().id("DEFAULT_SERVER").build();
    private static final ExternalServerDto DEFAULT_EXTERNAL_SERVER = ExternalServerDto.builder()
            .id("DEFAULT_EXTERNAL_SERVER")
            .image("DEFAULT_EXTERNAL_SERVER")
            .game(Game.DOFUS)
            .labels(Map.of(Language.FR, "DEFAULT_EXTERNAL_SERVER",
                    Language.EN, "DEFAULT_EXTERNAL_SERVER",
                    Language.ES, "DEFAULT_EXTERNAL_SERVER"))
            .build();

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private ServerService serverService;

    @BeforeEach
    void provideData(){
        serverRepository.save(DEFAULT_SERVER).block();
        Mockito.reset(serverService);
    }

    @Test
    @WithMockUser(authorities = {Privilege.READ_SERVER})
    void findByIdInternalExceptionTest(){
        Mockito.when(serverService.findById(DEFAULT_SERVER.getId())).thenThrow(new RuntimeException());
        webTestClient.get()
                .uri(API + SERVER_FIND_BY_ID.replace("{" + SERVER_VAR + "}", DEFAULT_SERVER.getId()))
                .exchange()
                .expectStatus().isEqualTo(INTERNAL_SERVER_ERROR)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }

    @Test
    @WithMockUser(authorities = {Privilege.READ_SERVER})
    void findAllInternalExceptionTest(){
        Mockito.when(serverService.findAll()).thenThrow(new RuntimeException());
        webTestClient.get()
                .uri(API + SERVER_FIND_ALL)
                .exchange()
                .expectStatus().isEqualTo(INTERNAL_SERVER_ERROR)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }

    @Test
    @WithMockUser(authorities = {Privilege.SAVE_SERVER})
    void saveInternalExceptionTest(){
        Mockito.when(serverService.save(Mockito.any())).thenThrow(new RuntimeException());
        webTestClient.post()
                .uri(API + SERVER_SAVE)
                .bodyValue(DEFAULT_EXTERNAL_SERVER)
                .exchange()
                .expectStatus().isEqualTo(INTERNAL_SERVER_ERROR)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }
}