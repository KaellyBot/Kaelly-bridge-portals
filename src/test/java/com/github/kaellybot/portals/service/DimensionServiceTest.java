package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.model.entity.Dimension;
import com.github.kaellybot.portals.repository.DimensionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DimensionServiceTest {

    private static final Dimension DEFAULT_DIMENSION = Dimension.builder().id("DEFAULT_DIMENSION").build();

    @Autowired
    private DimensionService dimensionService;

    @Autowired
    private DimensionRepository dimensionRepository;

    @BeforeEach
    void provideData(){
        dimensionRepository.save(DEFAULT_DIMENSION).block();
    }

    @Test
    void findPassingCaseTest(){
        StepVerifier.create(dimensionService.findById(DEFAULT_DIMENSION.getId()))
                .assertNext(server -> assertThat(server).isNotNull().isEqualTo(DEFAULT_DIMENSION))
                .expectComplete()
                .verify();
    }

    @Test
    void findNoPassingCaseTest(){
        StepVerifier.create(dimensionService.findById(DEFAULT_DIMENSION.getId() + "_BAD_NAME"))
                .expectComplete()
                .verify();
    }
}