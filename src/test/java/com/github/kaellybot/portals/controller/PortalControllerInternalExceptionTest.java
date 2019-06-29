package com.github.kaellybot.portals.controller;

import com.github.kaellybot.portals.model.constants.Dimension;
import com.github.kaellybot.portals.model.constants.Server;
import com.github.kaellybot.portals.model.dto.ExternalPortalDto;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

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
                .uri(API + "/" + Server.MERIANA + PORTALS + "?" + DIMENSION_VAR + "=" + Dimension.SRAMBAD + "&token=token")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                .expectBody(String.class)
                .consumeWith(t -> assertTrue(t.getResponseBody().contains(INTERNAL_SERVER_ERROR_MESSAGE)));
    }

    @Test
    void findAllByPortalIdServerInternalExceptionTest(){
        Mockito.when(portalService.findAllByPortalIdServer(Server.BRUMEN))
                .thenThrow(new NullPointerException());
        webTestClient.get()
                .uri(API + "/" + Server.MERIANA + PORTALS + "?token=token")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                .expectBody(String.class)
                .consumeWith(t -> assertTrue(t.getResponseBody().contains(INTERNAL_SERVER_ERROR_MESSAGE)));
    }

    @Test
    void addPortalInternalExceptionTest(){
        Mockito.when(portalService.findById(Server.MERIANA, Dimension.ECAFLIPUS))
                .thenThrow(new NullPointerException());
        webTestClient.post()
                .uri(API + "/" + Server.MERIANA + PORTALS + "?" + DIMENSION_VAR + "=" + Dimension.ECAFLIPUS
                        + "&token=token")
                .syncBody(ExternalPortalDto.builder().build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                .expectBody(String.class)
                .consumeWith(t -> assertTrue(t.getResponseBody().contains(INTERNAL_SERVER_ERROR_MESSAGE)));
    }
}
