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
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.kaellybot.portals.controller.PortalConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Test
    void findByIdExceptionTest(){
        webTestClient.get()
                .uri(API + "/NO_SERVER" + PORTALS + "?" + DIMENSION_VAR + "=" + Dimension.SRAMBAD + "&token=token")
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(ResponseStatusException.class)
                .consumeWith(t -> assertEquals(SERVER_NOT_FOUND, t.getResponseBody()));
        webTestClient.get()
                .uri(API + "/" + Server.MERIANA + PORTALS + "?" + DIMENSION_VAR + "=NO_DIMENSION&token=token")
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(ResponseStatusException.class)
                .consumeWith(t -> assertEquals(DIMENSION_NOT_FOUND, t.getResponseBody()));
    }

    @ParameterizedTest
    @MethodSource("getPortals")
    void findByIdTest(Portal portal){
        webTestClient.get()
                .uri(API + "/" + portal.getPortalId().getServer() + PORTALS + "?" + DIMENSION_VAR + "="
                        + portal.getPortalId().getDimension() + "&token=token")
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(PortalDto.class)
                .consumeWith(t -> assertEquals(PortalMapper.map(portal, DEFAULT_LANGUAGE), t.getResponseBody()));
    }

    @ParameterizedTest
    @MethodSource("getPortals")
    void findAllByPortalIdServerTest(Portal portal){
        webTestClient.get()
                .uri(API + "/" + portal.getPortalId().getServer() + PORTALS + "?token=token")
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBodyList(PortalDto.class)
                .consumeWith(t -> assertTrue(t.getResponseBody().contains(PortalMapper.map(portal, DEFAULT_LANGUAGE))));
    }

    @Test
    void findAllByPortalIdExceptionTest(){
        webTestClient.get()
                .uri(API + "/NO_SERVER" + PORTALS + "?token=token")
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(ResponseStatusException.class)
                .consumeWith(t -> assertEquals(SERVER_NOT_FOUND, t.getResponseBody()));
    }

    @ParameterizedTest
    @MethodSource("getExternalPortals")
    void addPortalTest(ExternalPortalDto portal){
        webTestClient.post()
                .uri(API + "/" + Server.BRUMEN + PORTALS + "?" + DIMENSION_VAR + "="
                        + Dimension.XELORIUM + "&token=token")
                .syncBody(portal)
                .exchange()
                .expectStatus().isEqualTo(OK);
    }

    @ParameterizedTest
    @MethodSource("getExternalPortals")
    void addPortalExceptionTest(ExternalPortalDto portal){
        webTestClient.post()
                .uri(API + "/NO_SERVER" + PORTALS + "?" + DIMENSION_VAR + "=" + Dimension.SRAMBAD + "&token=token")
                .syncBody(portal)
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(ResponseStatusException.class)
                .consumeWith(t -> assertEquals(SERVER_NOT_FOUND, t.getResponseBody()));
        webTestClient.post()
                .uri(API + "/" + Server.MERIANA + PORTALS + "?" + DIMENSION_VAR + "=NO_DIMENSION&token=token")
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(ResponseStatusException.class)
                .consumeWith(t -> assertEquals(DIMENSION_NOT_FOUND, t.getResponseBody()));
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
                        .nearestZaap(Transport.CITE_D_ASTRUB)
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
                        .nearestZaap(Transport.CITE_D_ASTRUB)
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