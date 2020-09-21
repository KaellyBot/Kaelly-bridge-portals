package com.github.kaellybot.portals.controller;

import com.github.kaellybot.commons.model.entity.Server;
import com.github.kaellybot.commons.repository.ServerRepository;
import com.github.kaellybot.commons.service.ServerService;
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

import static com.github.kaellybot.portals.controller.PortalConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureWebTestClient
class ServerControllerInternalExceptionTest {

    private static final Server DEFAULT_SERVER = Server.builder().id("DEFAULT_SERVER").build();

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
}