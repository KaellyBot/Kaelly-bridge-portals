package com.github.kaellybot.portals.controller;

import com.github.kaellybot.portals.model.constants.Dimension;
import com.github.kaellybot.portals.model.constants.Server;
import com.github.kaellybot.portals.model.dto.ExternalPortalDto;
import com.github.kaellybot.portals.model.dto.PositionDto;
import com.github.kaellybot.portals.service.PortalService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.kaellybot.portals.controller.PortalConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureWebTestClient
class PortalControllerInternalExceptionTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PortalService portalService;

    @Test
    void findByIdInternalExceptionTest(){
        Mockito.when(portalService.findById(Server.BRUMEN, Dimension.SRAMBAD))
                .thenThrow(new NullPointerException());
        webTestClient.get()
                .uri(API + FIND_BY_ID.replace("{" + SERVER_VAR + "}", Server.MERIANA.name())
                        .replace("{" + DIMENSION_VAR + "}", Dimension.SRAMBAD.name()))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull().contains(INTERNAL_SERVER_ERROR_MESSAGE));
    }

    @Test
    void findAllInternalExceptionTest(){
        Mockito.when(portalService.findAllByPortalIdServer(Server.BRUMEN))
                .thenThrow(new NullPointerException());
        webTestClient.get()
                .uri(API + FIND_ALL.replace("{" + SERVER_VAR + "}", Server.MERIANA.name()))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull().contains(INTERNAL_SERVER_ERROR_MESSAGE));
    }

    @Test
    void mergeInternalExceptionTest(){
        Mockito.when(portalService.findById(Server.MERIANA, Dimension.ECAFLIPUS))
                .thenThrow(new NullPointerException());
        webTestClient.patch()
                .uri(API + MERGE.replace("{" + SERVER_VAR + "}", Server.MERIANA.name())
                        .replace("{" + DIMENSION_VAR + "}", Dimension.ECAFLIPUS.name()))
                .bodyValue(ExternalPortalDto.builder()
                        .position(PositionDto.builder().x(0).y(0).build())
                        .build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull().contains(INTERNAL_SERVER_ERROR_MESSAGE));
    }
}
