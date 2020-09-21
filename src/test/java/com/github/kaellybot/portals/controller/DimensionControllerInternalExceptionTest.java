package com.github.kaellybot.portals.controller;

import com.github.kaellybot.commons.model.constants.Language;
import com.github.kaellybot.commons.model.entity.Dimension;
import com.github.kaellybot.commons.repository.DimensionRepository;
import com.github.kaellybot.commons.service.DimensionService;
import com.github.kaellybot.portals.model.dto.ExternalDimensionDto;
import com.github.kaellybot.portals.model.dto.ExternalServerDto;
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

import java.util.Map;

import static com.github.kaellybot.portals.controller.PortalConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureWebTestClient
class DimensionControllerInternalExceptionTest {

    private static final Dimension DEFAULT_DIMENSION = Dimension.builder().id("DEFAULT_DIMENSION").build();
    private static final ExternalDimensionDto DEFAULT_EXTERNAL_DIMENSION = ExternalDimensionDto.builder()
            .id("DEFAULT_EXTERNAL_DIMENSION")
            .image("DEFAULT_EXTERNAL_DIMENSION")
            .labels(Map.of(Language.FR, "DEFAULT_EXTERNAL_DIMENSION",
                    Language.EN, "DEFAULT_EXTERNAL_DIMENSION",
                    Language.ES, "DEFAULT_EXTERNAL_DIMENSION"))
            .build();

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
    @WithMockUser(authorities = {Privilege.READ_DIMENSION})
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
    @WithMockUser(authorities = {Privilege.READ_DIMENSION})
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

    @Test
    @WithMockUser(authorities = {Privilege.SAVE_DIMENSION})
    void saveInternalExceptionTest(){
        Mockito.when(dimensionService.save(Mockito.any())).thenThrow(new RuntimeException());
        webTestClient.post()
                .uri(API + DIMENSION_SAVE)
                .bodyValue(DEFAULT_EXTERNAL_DIMENSION)
                .exchange()
                .expectStatus().isEqualTo(INTERNAL_SERVER_ERROR)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }
}