package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.commons.model.constants.Language;
import com.github.kaellybot.commons.model.entity.Dimension;
import com.github.kaellybot.portals.model.dto.DimensionDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DimensionMapperTest {

    private static final Language DEFAULT_LANGUAGE = Language.EN;
    private static final String ENUTROSOR = "Enutrosor";
    private static final String URL = "http://image.png";
    private static final int COLOR = 1;

    @Autowired
    private DimensionMapper dimensionMapper;

    @ParameterizedTest
    @MethodSource("getDimensions")
    void mapPositionDtoTest(Dimension dimension)
    {
        DimensionDto result = dimensionMapper.map(dimension, DEFAULT_LANGUAGE);
        assertThat(result).isNotNull();
        if (Optional.ofNullable(dimension.getLabels()).map(map -> map.containsKey(DEFAULT_LANGUAGE)).orElse(false))
            assertThat(result.getName()).isNotNull().isEqualTo(dimension.getLabels().get(DEFAULT_LANGUAGE));
        else
            assertThat(result.getName()).isNull();

        if (dimension.getUrlImg() != null)
            assertThat(result.getImage()).isNotNull().isEqualTo(dimension.getUrlImg());
        else
            assertThat(result.getImage()).isNull();

        assertThat(result.getColor()).isEqualTo(dimension.getColor());
    }

    private static Stream<Dimension> getDimensions() {
        return Stream.of(Dimension.builder().urlImg(URL).labels(Map.of(DEFAULT_LANGUAGE, ENUTROSOR)).build(),
                Dimension.builder().labels(Map.of(DEFAULT_LANGUAGE, ENUTROSOR)).build(),
                Dimension.builder().color(COLOR).build(),
                Dimension.builder().build());
    }
}
