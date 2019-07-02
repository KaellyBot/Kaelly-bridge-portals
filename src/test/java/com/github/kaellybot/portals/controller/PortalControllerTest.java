package com.github.kaellybot.portals.controller;

import com.github.kaellybot.portals.mapper.PortalMapper;
import com.github.kaellybot.portals.model.constants.Dimension;
import com.github.kaellybot.portals.model.constants.Server;
import com.github.kaellybot.portals.model.constants.Transport;
import com.github.kaellybot.portals.model.dto.ExternalPortalDto;
import com.github.kaellybot.portals.model.dto.PortalDto;
import com.github.kaellybot.portals.model.dto.PositionDto;
import com.github.kaellybot.portals.model.entity.Author;
import com.github.kaellybot.portals.model.entity.Portal;
import com.github.kaellybot.portals.model.entity.PortalId;
import com.github.kaellybot.portals.model.entity.Position;
import com.github.kaellybot.portals.repository.PortalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Instant;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.kaellybot.portals.controller.PortalConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureWebTestClient
class PortalControllerTest {

    private final static Instant TIME = Instant.parse("2019-01-10T00:00:00.00Z");

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PortalRepository portalRepository;

    @BeforeEach
    void provideData(){
        portalRepository.saveAll(getPortals().collect(Collectors.toList()))
                .collectList()
                .block();
    }

    @ParameterizedTest
    @MethodSource("getPortals")
    void findByIdTest(Portal portal){
        webTestClient.get()
                .uri(API + "/" + portal.getPortalId().getServer() + PORTALS + "?" + DIMENSION_VAR + "="
                        + portal.getPortalId().getDimension())
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(PortalDto.class)
                .consumeWith(t -> assertEquals(PortalMapper.map(portal, DEFAULT_LANGUAGE), t.getResponseBody()));
    }

    @Test
    void findByIdExceptionTest(){
        webTestClient.get()
                .uri(API + "/NO_SERVER" + PORTALS + "?" + DIMENSION_VAR + "=" + Dimension.SRAMBAD)
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertTrue(t.getResponseBody().contains(SERVER_NOT_FOUND_MESSAGE)));
        webTestClient.get()
                .uri(API + "/" + Server.MERIANA + PORTALS + "?" + DIMENSION_VAR + "=NO_DIMENSION")
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertTrue(t.getResponseBody().contains(DIMENSION_NOT_FOUND_MESSAGE)));
        webTestClient.get()
                .uri(API + "/" + Server.MERIANA + PORTALS + "?" + DIMENSION_VAR + "=" + Dimension.SRAMBAD)
                .header(ACCEPT_LANGUAGE, "NO_LANGUAGE")
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertTrue(t.getResponseBody().contains(LANGUAGE_NOT_FOUND_MESSAGE)));
    }

    @ParameterizedTest
    @MethodSource("getPortals")
    void findAllByPortalIdServerTest(Portal portal){
        webTestClient.get()
                .uri(API + "/" + portal.getPortalId().getServer() + PORTALS)
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBodyList(PortalDto.class)
                .consumeWith(t -> assertTrue(t.getResponseBody().contains(PortalMapper.map(portal, DEFAULT_LANGUAGE))));
    }

    @Test
    void findAllByPortalIdExceptionTest(){
        webTestClient.get()
                .uri(API + "/NO_SERVER" + PORTALS)
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertTrue(t.getResponseBody().contains(SERVER_NOT_FOUND_MESSAGE)));
        webTestClient.get()
                .uri(API + "/" + Server.MERIANA + PORTALS)
                .header(ACCEPT_LANGUAGE, "NO_LANGUAGE")
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertTrue(t.getResponseBody().contains(LANGUAGE_NOT_FOUND_MESSAGE)));
    }

    @ParameterizedTest
    @MethodSource("getExternalPortals")
    void addPortalTest(ExternalPortalDto portal){
        webTestClient.post()
                .uri(API + "/" + Server.ATCHAM + PORTALS + "?" + DIMENSION_VAR + "=" + Dimension.ENUTROSOR)
                .syncBody(portal)
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(PortalDto.class);
    }

    @ParameterizedTest
    @MethodSource("getExternalPortals")
    void addPortalExceptionTest(ExternalPortalDto portal){
        webTestClient.post()
                .uri(API + "/NO_SERVER" + PORTALS + "?" + DIMENSION_VAR + "=" + Dimension.SRAMBAD)
                .syncBody(portal)
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertTrue(t.getResponseBody().contains(SERVER_NOT_FOUND_MESSAGE)));
        webTestClient.post()
                .uri(API + "/" + Server.MERIANA + PORTALS + "?" + DIMENSION_VAR + "=NO_DIMENSION")
                .syncBody(portal)
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertTrue(t.getResponseBody().contains(DIMENSION_NOT_FOUND_MESSAGE)));
        webTestClient.post()
                .uri(API + "/" + Server.MERIANA + PORTALS+ "?" + DIMENSION_VAR + "=" + Dimension.SRAMBAD)
                .header(ACCEPT_LANGUAGE, "NO_LANGUAGE")
                .syncBody(portal)
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertTrue(t.getResponseBody().contains(LANGUAGE_NOT_FOUND_MESSAGE)));
    }

    private static Stream<ExternalPortalDto> getExternalPortals(){
        return Stream.of(
                ExternalPortalDto.builder()
                        .position(PositionDto.builder().x(2).y(0).build())
                        .utilisation(42)
                        .build(),
                ExternalPortalDto.builder()
                        .position(PositionDto.builder().x(5).y(7).build())
                        .utilisation(42)
                        .build(),
                ExternalPortalDto.builder()
                        .position(PositionDto.builder().x(-20).y(20).build())
                        .build()
        );
    }

    private static Stream<Portal> getPortals(){
        return Stream.of(
                Portal.builder()
                        .portalId(PortalId.builder()
                            .server(Server.ATCHAM)
                            .dimension(Dimension.ENUTROSOR)
                            .build())
                        .isAvailable(true)
                        .position(Position.builder().x(10).y(20).build())
                        .creationDate(TIME)
                        .creationAuthor(Author.builder().build())
                        .nearestZaap(Transport.ZAAP_CITE_ASTRUB)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder()
                            .server(Server.AGRIDE)
                            .dimension(Dimension.ENUTROSOR)
                            .build())
                        .isAvailable(true)
                        .position(Position.builder().x(0).y(0).build())
                        .creationDate(TIME)
                        .creationAuthor(Author.builder().build())
                        .nearestZaap(Transport.ZAAP_BERCEAU)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder()
                            .server(Server.AGRIDE)
                            .dimension(Dimension.ECAFLIPUS)
                            .build())
                        .isAvailable(false)
                        .build()
        );
    }
}