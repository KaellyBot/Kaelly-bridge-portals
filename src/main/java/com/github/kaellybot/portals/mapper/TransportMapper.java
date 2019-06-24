package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.constants.Transport;
import com.github.kaellybot.portals.model.dto.TransportDto;

final class TransportMapper {

    private TransportMapper(){}

    static TransportDto map(Transport transport){
        return TransportDto.builder()
                .type(transport.getType().getKey())
                .area(transport.getArea().getName())
                .subArea(transport.getSubArea())
                .position(PositionMapper.map(transport.getPosition()))
                .isAvailableUnderConditions(transport.isAvailableUnderConditions())
                .build();
    }
}