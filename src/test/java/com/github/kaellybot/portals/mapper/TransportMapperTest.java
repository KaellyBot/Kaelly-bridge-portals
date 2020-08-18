package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.commons.util.Translator;
import com.github.kaellybot.portals.model.constants.Transport;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.kaellybot.portals.controller.PortalConstants.DEFAULT_LANGUAGE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class transportMapperTest {

    @Autowired private PositionMapper positionMapper;
    @Autowired private TransportMapper transportMapper;
    @Autowired private Translator translator;
    
    @ParameterizedTest
    @EnumSource(Transport.class)
    void mapTransportDtoTest(Transport transport)
    {
        assertThat(transportMapper.map(transport, DEFAULT_LANGUAGE)).isNotNull();
        assertThat(transportMapper.map(transport, DEFAULT_LANGUAGE).getType()).isNotNull();
        assertThat(transportMapper.map(transport, DEFAULT_LANGUAGE).getArea()).isNotNull();
        assertThat(transportMapper.map(transport, DEFAULT_LANGUAGE).getSubArea()).isNotNull();
        assertThat(transportMapper.map(transport, DEFAULT_LANGUAGE).getPosition()).isNotNull();

        assertThat(translator.getLabel(DEFAULT_LANGUAGE, transport.getType())).isNotNull()
                .isEqualTo(transportMapper.map(transport, DEFAULT_LANGUAGE).getType());
        assertThat(translator.getLabel(DEFAULT_LANGUAGE, transport.getSubArea().getArea())).isNotNull()
                .isEqualTo(transportMapper.map(transport, DEFAULT_LANGUAGE).getArea());
        assertThat(translator.getLabel(DEFAULT_LANGUAGE, transport.getSubArea())).isNotNull()
                .isEqualTo( transportMapper.map(transport, DEFAULT_LANGUAGE).getSubArea());
        assertThat(positionMapper.map(transport.getPosition())).isNotNull()
                .isEqualTo(transportMapper.map(transport, DEFAULT_LANGUAGE).getPosition());
        assertThat(transport.isAvailableUnderConditions()).isNotNull()
                .isEqualTo(transportMapper.map(transport, DEFAULT_LANGUAGE).isAvailableUnderConditions());
    }
}