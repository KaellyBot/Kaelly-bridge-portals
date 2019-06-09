package com.github.kaellybot.portals.mapper;

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

    private static Stream<Position> getPositions() {
        return Stream.of(Position.builder().x(0).y(0).build(),
                Position.builder().x(5).y(7).build(),
                Position.builder().x(-20).y(-20).build(),
                Position.builder().x(-50).y(50).build(),
                Position.builder().x(666).y(666).build());
    }
}