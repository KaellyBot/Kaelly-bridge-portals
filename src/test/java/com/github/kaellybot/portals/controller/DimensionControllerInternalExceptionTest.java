package com.github.kaellybot.portals.controller;

import com.github.kaellybot.commons.model.entity.Dimension;
import com.github.kaellybot.commons.repository.DimensionRepository;
import com.github.kaellybot.commons.service.DimensionService;
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
class DimensionControllerInternalExceptionTest {

    private static final Dimension DEFAULT_DIMENSION = Dimension.builder().id("DEFAULT_DIMENSION").build();

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private DimensionRepository dimensionRepository;

    @Autowired
    private DimensionService dimensionService;

    @BeforeEach
    void provideData(){
        dimensionRepository.save(DEFAULT_DIMENSION).block();
        Mockito.reset(dimensionService);
    }

    @Test
    void findByIdInternalExceptionTest(){
        Mockito.when(dimensionService.findById(DEFAULT_DIMENSION.getId())).thenThrow(new RuntimeException());
        webTestClient.get()
                .uri(API + DIMENSION_FIND_BY_ID.replace("{" + DIMENSION_VAR + "}", DEFAULT_DIMENSION.getId()))
                .exchange()
                .expectStatus().isEqualTo(INTERNAL_SERVER_ERROR)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }

    @Test
    void findAllInternalExceptionTest(){
        Mockito.when(dimensionService.findAll()).thenThrow(new RuntimeException());
        webTestClient.get()
                .uri(API + DIMENSION_FIND_ALL)
                .exchange()
                .expectStatus().isEqualTo(INTERNAL_SERVER_ERROR)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }
}