package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.constants.Transport;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static com.github.kaellybot.portals.controller.PortalConstants.DEFAULT_LANGUAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TransportMapperTest {

    @ParameterizedTest
    @EnumSource(Transport.class)
    void mapTransportDtoTest(Transport transport)
    {
        assertNotNull(TransportMapper.map(transport, DEFAULT_LANGUAGE));
        assertNotNull(TransportMapper.map(transport, DEFAULT_LANGUAGE).getType());
        assertNotNull(TransportMapper.map(transport, DEFAULT_LANGUAGE).getArea());
        assertNotNull(TransportMapper.map(transport, DEFAULT_LANGUAGE).getSubArea());
        assertNotNull(TransportMapper.map(transport, DEFAULT_LANGUAGE).getPosition());

        assertEquals(transport.getType().getLabel(DEFAULT_LANGUAGE),
                TransportMapper.map(transport, DEFAULT_LANGUAGE).getType());
        assertEquals(transport.getArea().getLabel(DEFAULT_LANGUAGE),
                TransportMapper.map(transport, DEFAULT_LANGUAGE).getArea());
        assertEquals(transport.getSubArea(), TransportMapper.map(transport, DEFAULT_LANGUAGE).getSubArea());
        assertEquals(PositionMapper.map(transport.getPosition()),
                TransportMapper.map(transport, DEFAULT_LANGUAGE).getPosition());
        assertEquals(transport.isAvailableUnderConditions(),
                TransportMapper.map(transport, DEFAULT_LANGUAGE).isAvailableUnderConditions());
    }
}