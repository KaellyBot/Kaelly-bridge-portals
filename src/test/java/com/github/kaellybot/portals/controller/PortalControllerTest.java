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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Autowired
    private PortalMapper portalMapper;

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
                .uri(API + FIND_BY_ID.replace("{" + SERVER_VAR + "}", portal.getPortalId().getServer().name())
                                .replace("{" + DIMENSION_VAR + "}", portal.getPortalId().getDimension().name()))
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(PortalDto.class)
                .consumeWith(t -> assertEquals(portalMapper.map(portal, DEFAULT_LANGUAGE), t.getResponseBody()));
    }

    @Test
    void findByIdExceptionTest(){
        webTestClient.get()
                .uri(API + FIND_BY_ID.replace("{" + SERVER_VAR + "}", "NO_SERVER")
                        .replace("{" + DIMENSION_VAR + "}", Dimension.SRAMBAD.name()))
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull().contains(SERVER_NOT_FOUND_MESSAGE));
        webTestClient.get()
                .uri(API + FIND_BY_ID.replace("{" + SERVER_VAR + "}", Server.MERIANA.name())
                        .replace("{" + DIMENSION_VAR + "}", "NO_DIMENSION"))
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull().contains(DIMENSION_NOT_FOUND_MESSAGE));
        webTestClient.get()
                .uri(API + FIND_BY_ID.replace("{" + SERVER_VAR + "}", Server.MERIANA.name())
                        .replace("{" + DIMENSION_VAR + "}", Dimension.SRAMBAD.name()))
                .header(ACCEPT_LANGUAGE, "NO_LANGUAGE")
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull().contains(LANGUAGE_NOT_FOUND_MESSAGE));
    }

    @ParameterizedTest
    @MethodSource("getPortals")
    void findAllTest(Portal portal){
        webTestClient.get()
                .uri(API + FIND_ALL.replace("{" + SERVER_VAR + "}", portal.getPortalId().getServer().name()))
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBodyList(PortalDto.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull().contains(portalMapper.map(portal, DEFAULT_LANGUAGE)));
    }

    @Test
    void findAllExceptionTest(){
        webTestClient.get()
                .uri(API + FIND_ALL.replace("{" + SERVER_VAR + "}", "NO_SERVER"))
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull().contains(SERVER_NOT_FOUND_MESSAGE));
        webTestClient.get()
                .uri(API + FIND_ALL.replace("{" + SERVER_VAR + "}", Server.MERIANA.name()))
                .header(ACCEPT_LANGUAGE, "NO_LANGUAGE")
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull().contains(LANGUAGE_NOT_FOUND_MESSAGE));
    }

    @ParameterizedTest
    @MethodSource("getExternalPortals")
    void mergeTest(ExternalPortalDto portal){
        webTestClient.patch()
                .uri(API + MERGE.replace("{" + SERVER_VAR + "}", Server.ATCHAM.name())
                        .replace("{" + DIMENSION_VAR + "}", Dimension.ENUTROSOR.name()))
                .bodyValue(portal)
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(PortalDto.class);
    }

    @ParameterizedTest
    @MethodSource("getExternalPortals")
    void mergeExceptionTest(ExternalPortalDto portal){
        webTestClient.patch()
                .uri(API + MERGE.replace("{" + SERVER_VAR + "}", "NO_SERVER")
                        .replace("{" + DIMENSION_VAR + "}", Dimension.SRAMBAD.name()))
                .bodyValue(portal)
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull().contains(SERVER_NOT_FOUND_MESSAGE));
        webTestClient.patch()
                .uri(API + MERGE.replace("{" + SERVER_VAR + "}", Server.MERIANA.name())
                        .replace("{" + DIMENSION_VAR + "}", "NO_DIMENSION"))
                .bodyValue(portal)
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull().contains(DIMENSION_NOT_FOUND_MESSAGE));
        webTestClient.patch()
                .uri(API + MERGE.replace("{" + SERVER_VAR + "}", Server.MERIANA.name())
                        .replace("{" + DIMENSION_VAR + "}", Dimension.SRAMBAD.name()))
                .header(ACCEPT_LANGUAGE, "NO_LANGUAGE")
                .bodyValue(portal)
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull().contains(LANGUAGE_NOT_FOUND_MESSAGE));
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