package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.model.constants.Dimension;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DimensionServiceTest {

    @Autowired
    private IDimensionService dimensionService;

    @ParameterizedTest
    @EnumSource(Dimension.class)
    void findPassingCaseTest(Dimension dimension){
        assertAll(
                () -> assertTrue(dimensionService.findByName(dimension.name()).isPresent()),
                () -> assertTrue(dimensionService.findByName(dimension.name().toLowerCase()).isPresent()),
                () -> assertTrue(dimensionService.findByName(dimension.name().toUpperCase()).isPresent()),
                () -> assertTrue(dimensionService.findByName(StringUtils.stripAccents(dimension.name())).isPresent())
        );

        dimensionService.findByName(dimension.name())
                .ifPresent(dim -> assertEquals(dimension, dim));
    }

    @ParameterizedTest
    @EnumSource(Dimension.class)
    void findNoPassingCaseTest(Dimension dimension){
        assertFalse(dimensionService.findByName(dimension.name() + "_BAD_NAME").isPresent());
    }
}