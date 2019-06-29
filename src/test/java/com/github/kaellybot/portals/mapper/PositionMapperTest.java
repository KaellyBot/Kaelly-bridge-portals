package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.dto.PositionDto;
import com.github.kaellybot.portals.model.entity.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PositionMapperTest {

    @ParameterizedTest
    @MethodSource("getPositions")
    void mapPositionDtoTest(Position position)
    {
        assertNotNull(PositionMapper.map(position));
        assertEquals(position.getX(), PositionMapper.map(position).getX());
        assertEquals(position.getY(), PositionMapper.map(position).getY());
    }

    @ParameterizedTest
    @MethodSource("getPositionDtos")
    void mapPositionDtoTest(PositionDto position)
    {
        assertNotNull(PositionMapper.map(position));
        assertEquals(position.getX(), PositionMapper.map(position).getX());
        assertEquals(position.getY(), PositionMapper.map(position).getY());
    }

    @ParameterizedTest
    @MethodSource("getPositions")
    void endToEndMapTest(Position position)
    {
        assertNotNull(PositionMapper.map(PositionMapper.map(position)));
        assertEquals(position, PositionMapper.map(PositionMapper.map(position)));
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