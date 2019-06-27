package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.constants.Language;
import com.github.kaellybot.portals.model.constants.Transport;
import com.github.kaellybot.portals.model.dto.TransportDto;

final class TransportMapper {

    private TransportMapper(){}

    static TransportDto map(Transport transport, Language language){
        return TransportDto.builder()
                .type(transport.getType().getLabel(language))
                .area(transport.getArea().getLabel(language))
                .subArea(transport.getSubArea())
                .position(PositionMapper.map(transport.getPosition()))
                .isAvailableUnderConditions(transport.isAvailableUnderConditions())
                .build();
    }
}