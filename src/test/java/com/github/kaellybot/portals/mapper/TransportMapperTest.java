package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.constants.Transport;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TransportMapperTest {

    @ParameterizedTest
    @EnumSource(Transport.class)
    void mapTransportDtoTest(Transport transport)
    {
        assertNotNull(TransportMapper.map(transport));
        assertNotNull(TransportMapper.map(transport).getType());
        assertNotNull(TransportMapper.map(transport).getArea());
        assertNotNull(TransportMapper.map(transport).getSubArea());
        assertNotNull(TransportMapper.map(transport).getPosition());

        assertEquals(transport.getType().getName(), TransportMapper.map(transport).getType());
        assertEquals(transport.getArea().getName(), TransportMapper.map(transport).getArea());
        assertEquals(transport.getSubArea(), TransportMapper.map(transport).getSubArea());
        assertEquals(PositionMapper.map(transport.getPosition()), TransportMapper.map(transport).getPosition());
        assertEquals(transport.isAvailableUnderConditions(), TransportMapper.map(transport).isAvailableUnderConditions());
    }
}