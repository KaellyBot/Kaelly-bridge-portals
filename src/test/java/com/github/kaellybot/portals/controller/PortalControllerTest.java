package com.github.kaellybot.portals.controller;

import com.github.kaellybot.commons.model.entity.Dimension;
import com.github.kaellybot.commons.model.entity.Server;
import com.github.kaellybot.commons.repository.DimensionRepository;
import com.github.kaellybot.commons.repository.ServerRepository;
import com.github.kaellybot.portals.mapper.PortalMapper;
import com.github.kaellybot.portals.model.constants.Transport;
import com.github.kaellybot.portals.model.dto.ExternalPortalDto;
import com.github.kaellybot.portals.model.dto.PortalDto;
import com.github.kaellybot.portals.model.dto.PositionDto;
import com.github.kaellybot.portals.model.entity.*;
import com.github.kaellybot.portals.repository.PortalRepository;
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

import java.time.Instant;
import java.util.Map;
import java.util.function.Function;
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

    private static final Server DEFAULT_SERVER = Server.builder().id("DEFAULT_SERVER").build();

    private static final Dimension DEFAULT_DIMENSION = Dimension.builder().id("DEFAULT_DIMENSION").build();

    private static final Map<String, Dimension> DIMENSIONS = getPortals()
            .map(portal -> Dimension.builder().id(portal.getPortalId().getDimensionId()).build())
            .collect(Collectors.toMap(Dimension::getId, Function.identity()));

    private final static Instant TIME = Instant.parse("2019-01-10T00:00:00.00Z");

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PortalRepository portalRepository;

    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private DimensionRepository dimensionRepository;

    @Autowired
    private PortalMapper portalMapper;

    @BeforeEach
    void provideData(){
        serverRepository.save(DEFAULT_SERVER)
                .then(dimensionRepository.save(DEFAULT_DIMENSION))
                .thenMany(dimensionRepository.saveAll(DIMENSIONS.values()))
                .thenMany(portalRepository.saveAll(getPortals().collect(Collectors.toList())))
                .collectList()
                .block();
    }

    @ParameterizedTest
    @WithMockUser(authorities = {Privilege.READ_PORTAL})
    @MethodSource("getPortals")
    void findByIdTest(Portal portal){
        webTestClient.get()
                .uri(API + PORTAL_FIND_BY_ID.replace("{" + SERVER_VAR + "}", portal.getPortalId().getServerId())
                                .replace("{" + DIMENSION_VAR + "}", portal.getPortalId().getDimensionId()))
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(PortalDto.class)
                .consumeWith(t -> assertEquals(portalMapper
                        .map(portal, DEFAULT_SERVER, DIMENSIONS.get(portal.getPortalId().getDimensionId()), DEFAULT_LANGUAGE),
                        t.getResponseBody()));
    }

    @Test
    @WithMockUser(authorities = {Privilege.READ_PORTAL})
    void findByIdExceptionTest(){
        webTestClient.get()
                .uri(API + PORTAL_FIND_BY_ID.replace("{" + SERVER_VAR + "}", "NO_SERVER")
                        .replace("{" + DIMENSION_VAR + "}", DEFAULT_DIMENSION.getId()))
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull().contains(SERVER_NOT_FOUND_MESSAGE));
        webTestClient.get()
                .uri(API + PORTAL_FIND_BY_ID.replace("{" + SERVER_VAR + "}", DEFAULT_SERVER.getId())
                        .replace("{" + DIMENSION_VAR + "}", "NO_DIMENSION"))
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull().contains(DIMENSION_NOT_FOUND_MESSAGE));
        webTestClient.get()
                .uri(API + PORTAL_FIND_BY_ID.replace("{" + SERVER_VAR + "}",  DEFAULT_SERVER.getId())
                        .replace("{" + DIMENSION_VAR + "}", DEFAULT_DIMENSION.getId()))
                .header(ACCEPT_LANGUAGE, "NO_LANGUAGE")
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(PortalDto.class);
    }

    @ParameterizedTest
    @WithMockUser(authorities = {Privilege.READ_PORTAL})
    @MethodSource("getPortals")
    void findAllTest(Portal portal){
        webTestClient.get()
                .uri(API + PORTAL_FIND_ALL.replace("{" + SERVER_VAR + "}", portal.getPortalId().getServerId()))
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBodyList(PortalDto.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(portalMapper.map(portal, DEFAULT_SERVER,
                                DIMENSIONS.get(portal.getPortalId().getDimensionId()), DEFAULT_LANGUAGE)));
    }

    @Test
    @WithMockUser(authorities = {Privilege.READ_PORTAL})
    void findAllExceptionTest(){
        webTestClient.get()
                .uri(API + PORTAL_FIND_ALL.replace("{" + SERVER_VAR + "}", "NO_SERVER"))
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull().contains(SERVER_NOT_FOUND_MESSAGE));
        webTestClient.get()
                .uri(API + PORTAL_FIND_ALL.replace("{" + SERVER_VAR + "}", DEFAULT_SERVER.getId()))
                .header(ACCEPT_LANGUAGE, "NO_LANGUAGE")
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBodyList(PortalDto.class);
    }

    @ParameterizedTest
    @WithMockUser(authorities = {Privilege.MERGE_PORTAL})
    @MethodSource("getExternalPortals")
    void mergeTest(ExternalPortalDto portal){
        webTestClient.patch()
                .uri(API + PORTAL_MERGE.replace("{" + SERVER_VAR + "}",  DEFAULT_SERVER.getId())
                        .replace("{" + DIMENSION_VAR + "}", DEFAULT_DIMENSION.getId()))
                .bodyValue(portal)
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(PortalDto.class);
    }

    @ParameterizedTest
    @WithMockUser(authorities = {Privilege.MERGE_PORTAL})
    @MethodSource("getExternalPortals")
    void mergeExceptionTest(ExternalPortalDto portal){
        webTestClient.patch()
                .uri(API + PORTAL_MERGE.replace("{" + SERVER_VAR + "}", "NO_SERVER")
                        .replace("{" + DIMENSION_VAR + "}", DEFAULT_DIMENSION.getId()))
                .bodyValue(portal)
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull().contains(SERVER_NOT_FOUND_MESSAGE));
        webTestClient.patch()
                .uri(API + PORTAL_MERGE.replace("{" + SERVER_VAR + "}",  DEFAULT_SERVER.getId())
                        .replace("{" + DIMENSION_VAR + "}", "NO_DIMENSION"))
                .bodyValue(portal)
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull().contains(DIMENSION_NOT_FOUND_MESSAGE));
        webTestClient.patch()
                .uri(API + PORTAL_MERGE.replace("{" + SERVER_VAR + "}",  DEFAULT_SERVER.getId())
                        .replace("{" + DIMENSION_VAR + "}", DEFAULT_DIMENSION.getId()))
                .header(ACCEPT_LANGUAGE, "NO_LANGUAGE")
                .bodyValue(portal)
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(PortalDto.class);
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
                            .serverId(DEFAULT_SERVER.getId())
                            .dimensionId(DEFAULT_DIMENSION.getId() + "_1")
                            .build())
                        .isAvailable(true)
                        .position(Position.builder().x(10).y(20).build())
                        .creationDate(TIME)
                        .creationAuthor(Author.builder().build())
                        .nearestZaap(Transport.ZAAP_CITE_ASTRUB)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder()
                            .serverId(DEFAULT_SERVER.getId())
                            .dimensionId(DEFAULT_DIMENSION.getId() + "_2")
                            .build())
                        .isAvailable(true)
                        .position(Position.builder().x(0).y(0).build())
                        .creationDate(TIME)
                        .creationAuthor(Author.builder().build())
                        .nearestZaap(Transport.ZAAP_BERCEAU)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder()
                            .serverId(DEFAULT_SERVER.getId())
                            .dimensionId(DEFAULT_DIMENSION.getId() + "_3")
                            .build())
                        .isAvailable(false)
                        .build()
        );
    }
}