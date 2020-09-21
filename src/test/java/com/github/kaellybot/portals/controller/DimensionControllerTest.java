package com.github.kaellybot.portals.controller;

import com.github.kaellybot.commons.model.entity.Dimension;
import com.github.kaellybot.commons.repository.DimensionRepository;
import com.github.kaellybot.portals.mapper.DimensionMapper;
import com.github.kaellybot.portals.model.dto.DimensionDto;
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

import java.util.Map;
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
class DimensionControllerTest {

    private static final Dimension DEFAULT_DIMENSION = Dimension.builder().id("DEFAULT_DIMENSION").build();
    private static final String ENUTROSOR = "Enutrosor";
    private static final String SRAMBAD = "Srambad";
    private static final String ECAFLIPUS = "Ecaflipus";
    private static final String XELORIUM = "Xélorium";
    private static final String URL = "http://image.png";
    private static final int COLOR = 1;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private DimensionRepository dimensionRepository;

    @Autowired
    private DimensionMapper dimensionMapper;

    @BeforeEach
    void provideData(){
        dimensionRepository.saveAll(getDimensions().collect(Collectors.toList()))
                .then(dimensionRepository.save(DEFAULT_DIMENSION)).block();
    }

    @ParameterizedTest
    @WithMockUser(authorities = {Privilege.READ_DIMENSION})
    @MethodSource("getDimensions")
    void findByIdTest(Dimension dimension){
        webTestClient.get()
                .uri(API + DIMENSION_FIND_BY_ID.replace("{" + DIMENSION_VAR + "}", dimension.getId()))
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(DimensionDto.class)
                .consumeWith(t -> assertEquals(dimensionMapper.map(dimension, DEFAULT_LANGUAGE), t.getResponseBody()));
    }

    @Test
    @WithMockUser(authorities = {Privilege.READ_DIMENSION})
    void findByIdExceptionTest(){
        webTestClient.get()
                .uri(API + DIMENSION_FIND_BY_ID.replace("{" + DIMENSION_VAR + "}", "NO_DIMENSION"))
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull().contains(DIMENSION_NOT_FOUND_MESSAGE));

        webTestClient.get()
                .uri(API + DIMENSION_FIND_BY_ID.replace("{" + DIMENSION_VAR + "}",  DEFAULT_DIMENSION.getId()))
                .header(ACCEPT_LANGUAGE, "NO_LANGUAGE")
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(DimensionDto.class);
    }

    @ParameterizedTest
    @WithMockUser(authorities = {Privilege.READ_DIMENSION})
    @MethodSource("getDimensions")
    void findAllTest(Dimension dimension){
        webTestClient.get()
                .uri(API + DIMENSION_FIND_ALL)
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBodyList(DimensionDto.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(dimensionMapper.map(dimension, DEFAULT_LANGUAGE)));
    }

    @Test
    @WithMockUser(authorities = {Privilege.READ_DIMENSION})
    void findAllExceptionTest(){
        webTestClient.get()
                .uri(API + DIMENSION_FIND_ALL)
                .header(ACCEPT_LANGUAGE, "NO_LANGUAGE")
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBodyList(DimensionDto.class);
    }

    private static Stream<Dimension> getDimensions() {
        return Stream.of(Dimension.builder().id(ENUTROSOR).urlImg(URL).labels(Map.of(DEFAULT_LANGUAGE, ENUTROSOR)).build(),
                Dimension.builder().id(SRAMBAD).labels(Map.of(DEFAULT_LANGUAGE, SRAMBAD)).build(),
                Dimension.builder().id(XELORIUM).color(COLOR).build(),
                Dimension.builder().id(ECAFLIPUS).build());
    }
}