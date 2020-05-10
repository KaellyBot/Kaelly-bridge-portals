package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.dto.PositionDto;
import com.github.kaellybot.portals.model.entity.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PositionMapperTest {

    @Autowired
    private PositionMapper positionMapper;

    @ParameterizedTest
    @MethodSource("getPositions")
    void mapPositionDtoTest(Position position)
    {
        assertThat(positionMapper.map(position)).isNotNull();
        assertThat(position.getX()).isNotNull().isEqualTo(positionMapper.map(position).getX());
        assertThat(position.getY()).isNotNull().isEqualTo(positionMapper.map(position).getY());
    }

    @ParameterizedTest
    @MethodSource("getPositionDtos")
    void mapPositionDtoTest(PositionDto position)
    {
        assertThat(positionMapper.map(position)).isNotNull();
        assertThat(position.getX()).isNotNull().isEqualTo(positionMapper.map(position).getX());
        assertThat(position.getY()).isNotNull().isEqualTo(positionMapper.map(position).getY());
    }

    @ParameterizedTest
    @MethodSource("getPositions")
    void endToEndMapTest(Position position)
    {
        assertThat(positionMapper.map(positionMapper.map(position))).isNotNull();
        assertThat(position).isNotNull().isEqualTo(positionMapper.map(positionMapper.map(position)));
    }

    private static Stream<Position> getPositions() {
        return Stream.of(Position.builder().x(0).y(0).build(),
                Position.builder().x(5).y(7).build(),
                Position.builder().x(-20).y(-20).build(),
                Position.builder().x(-50).y(50).build(),
                Position.builder().x(666).y(666).build());
    }

    private static Stream<PositionDto> getPositionDtos() {
        return Stream.of(PositionDto.builder().x(0).y(0).build(),
                PositionDto.builder().x(5).y(7).build(),
                PositionDto.builder().x(-20).y(-20).build(),
                PositionDto.builder().x(-50).y(50).build(),
                PositionDto.builder().x(666).y(666).build());
    }
}