package com.github.kaellybot.portals.controller;

import com.github.kaellybot.commons.model.entity.Dimension;
import com.github.kaellybot.commons.model.entity.Server;
import com.github.kaellybot.commons.repository.DimensionRepository;
import com.github.kaellybot.commons.repository.ServerRepository;
import com.github.kaellybot.portals.model.dto.ExternalPortalDto;
import com.github.kaellybot.portals.model.dto.PositionDto;
import com.github.kaellybot.portals.service.PortalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.kaellybot.portals.controller.PortalConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureWebTestClient
class PortalControllerInternalExceptionTest {

    private static final Server DEFAULT_SERVER = Server.builder().id("DEFAULT_SERVER").build();

    private static final Dimension DEFAULT_DIMENSION = Dimension.builder().id("DEFAULT_DIMENSION").build();

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PortalService portalService;

    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private DimensionRepository dimensionRepository;

    @BeforeEach
    void provideData(){
        serverRepository.save(DEFAULT_SERVER).then(dimensionRepository.save(DEFAULT_DIMENSION)).block();
        Mockito.reset(portalService);
    }

    @Test
    void findByIdInternalExceptionTest(){
        Mockito.when(portalService.findById(DEFAULT_SERVER, DEFAULT_DIMENSION))
                .thenThrow(new RuntimeException());
        webTestClient.get()
                .uri(API + FIND_BY_ID.replace("{" + SERVER_VAR + "}", DEFAULT_SERVER.getId())
                        .replace("{" + DIMENSION_VAR + "}", DEFAULT_DIMENSION.getId()))
                .exchange()
                .expectStatus().isEqualTo(INTERNAL_SERVER_ERROR)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }

    @Test
    void findAllInternalExceptionTest(){
        Mockito.when(portalService.findAllByPortalIdServer(DEFAULT_SERVER))
                .thenThrow(new RuntimeException());
        webTestClient.get()
                .uri(API + FIND_ALL.replace("{" + SERVER_VAR + "}", DEFAULT_SERVER.getId()))
                .exchange()
                .expectStatus().isEqualTo(INTERNAL_SERVER_ERROR)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }

    @Test
    void mergeInternalExceptionTest(){
        Mockito.when(portalService.findById(DEFAULT_SERVER, DEFAULT_DIMENSION))
                .thenThrow(new RuntimeException());
        webTestClient.patch()
                .uri(API + MERGE.replace("{" + SERVER_VAR + "}", DEFAULT_SERVER.getId())
                        .replace("{" + DIMENSION_VAR + "}", DEFAULT_DIMENSION.getId()))
                .bodyValue(ExternalPortalDto.builder()
                        .position(PositionDto.builder().x(0).y(0).build())
                        .build())
                .exchange()
                .expectStatus().isEqualTo(INTERNAL_SERVER_ERROR)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }
}