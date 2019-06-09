package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.constants.Transport;
import com.github.kaellybot.portals.model.dto.TransportDto;

class TransportMapper {

    static TransportDto map(Transport transport){
        return TransportDto.builder()
                .type(transport.getType())
                .area(transport.getArea())
                .subArea(transport.getSubArea())
                .position(PositionMapper.map(transport.getPosition()))
                .isAvailableUnderConditions(transport.isAvailableUnderConditions())
                .build();
    }
}
