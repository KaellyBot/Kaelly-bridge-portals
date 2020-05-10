package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.constants.Transport;
import com.github.kaellybot.portals.util.Translator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.kaellybot.portals.controller.PortalConstants.DEFAULT_LANGUAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class transportMapperTest {

    @Autowired private PositionMapper positionMapper;
    @Autowired private TransportMapper transportMapper;
    @Autowired private Translator translator;
    
    @ParameterizedTest
    @EnumSource(Transport.class)
    void mapTransportDtoTest(Transport transport)
    {
        assertNotNull(transportMapper.map(transport, DEFAULT_LANGUAGE));
        assertNotNull(transportMapper.map(transport, DEFAULT_LANGUAGE).getType());
        assertNotNull(transportMapper.map(transport, DEFAULT_LANGUAGE).getArea());
        assertNotNull(transportMapper.map(transport, DEFAULT_LANGUAGE).getSubArea());
        assertNotNull(transportMapper.map(transport, DEFAULT_LANGUAGE).getPosition());

        assertEquals(translator.getLabel(DEFAULT_LANGUAGE, transport.getType()),
                transportMapper.map(transport, DEFAULT_LANGUAGE).getType());
        assertEquals(translator.getLabel(DEFAULT_LANGUAGE, transport.getSubArea().getArea()),
                transportMapper.map(transport, DEFAULT_LANGUAGE).getArea());
        assertEquals(translator.getLabel(DEFAULT_LANGUAGE, transport.getSubArea()), transportMapper.map(transport, DEFAULT_LANGUAGE).getSubArea());
        assertEquals(positionMapper.map(transport.getPosition()),
                transportMapper.map(transport, DEFAULT_LANGUAGE).getPosition());
        assertEquals(transport.isAvailableUnderConditions(),
                transportMapper.map(transport, DEFAULT_LANGUAGE).isAvailableUnderConditions());
    }
}