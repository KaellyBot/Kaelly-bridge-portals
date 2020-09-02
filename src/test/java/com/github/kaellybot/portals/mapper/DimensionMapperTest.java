package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.commons.model.constants.Language;
import com.github.kaellybot.commons.model.entity.Dimension;
import com.github.kaellybot.portals.model.dto.DimensionDto;
import com.github.kaellybot.portals.model.dto.ExternalDimensionDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DimensionMapperTest {

    private static final Language DEFAULT_LANGUAGE = Language.EN;
    private static final String ENUTROSOR = "Enutrosor";
    private static final String URL = "http://image.png";

    @Autowired
    private DimensionMapper dimensionMapper;

    @ParameterizedTest
    @MethodSource("getDimensions")
    void mapPositionDtoTest(Dimension dimension)
    {
        DimensionDto result = dimensionMapper.map(dimension, DEFAULT_LANGUAGE);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull().isEqualTo(dimension.getId());
        assertThat(result.getName()).isNotNull().isEqualTo(dimension.getLabels().get(DEFAULT_LANGUAGE));
        assertThat(result.getImage()).isNotNull().isEqualTo(dimension.getUrlImg());
        assertThat(result.getColor()).isEqualTo(dimension.getColor());
    }

    @ParameterizedTest
    @MethodSource("getExternalDimensions")
    void mapPositionDtoTest(ExternalDimensionDto dimension)
    {
        Dimension result = dimensionMapper.map(dimension);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull().isEqualTo(dimension.getId());
        assertThat(result.getLabels()).isNotNull().isEqualTo(dimension.getLabels());
        assertThat(result.getUrlImg()).isNotNull().isEqualTo(dimension.getImage());
        assertThat(result.getColor()).isEqualTo(dimension.getColor());
    }

    private static Stream<Dimension> getDimensions() {
        return Stream.of(Dimension.builder().id(ENUTROSOR).urlImg(URL).labels(Map.of(DEFAULT_LANGUAGE, ENUTROSOR)).build());
    }

    private static Stream<ExternalDimensionDto> getExternalDimensions() {
        return Stream.of(ExternalDimensionDto.builder().id(ENUTROSOR).image(URL).labels(Map.of(DEFAULT_LANGUAGE, ENUTROSOR)).build());
    }
}
