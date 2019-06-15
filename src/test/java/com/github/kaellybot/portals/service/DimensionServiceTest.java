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
                () -> assertTrue(dimensionService.findByName(dimension.getName()).isPresent()),
                () -> assertTrue(dimensionService.findByName(dimension.getName().toLowerCase()).isPresent()),
                () -> assertTrue(dimensionService.findByName(dimension.getName().toUpperCase()).isPresent()),
                () -> assertTrue(dimensionService.findByName(StringUtils.stripAccents(dimension.getName())).isPresent())
        );

        dimensionService.findByName(dimension.getName())
                .ifPresent(dim -> assertEquals(dimension, dim));
    }

    @ParameterizedTest
    @EnumSource(Dimension.class)
    void findNoPassingCaseTest(Dimension dimension){
        assertFalse(dimensionService.findByName(dimension.getName() + "_BAD_NAME").isPresent());
    }
}