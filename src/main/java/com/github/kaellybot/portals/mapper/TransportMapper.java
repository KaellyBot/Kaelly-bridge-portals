package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.constants.Transport;
import com.github.kaellybot.portals.model.dto.TransportDto;

public class TransportMapper {

    public static TransportDto map(Transport transport){
        return new TransportDto().withType(transport.getType())
                .withArea(transport.getArea())
                .withSubArea(transport.getSubArea())
                .withPosition(PositionMapper.map(transport.getPosition()))
                .withAvailableUnderConditions(transport.isAvailableUnderConditions());
    }
}
