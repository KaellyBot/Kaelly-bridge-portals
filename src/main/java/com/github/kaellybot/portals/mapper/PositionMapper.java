package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.dto.PositionDto;
import com.github.kaellybot.portals.model.entity.Position;

final class PositionMapper {

    private PositionMapper(){}

    static PositionDto map(Position position){
        return PositionDto.builder()
                .x(position.getX())
                .y(position.getY())
                .build();
    }
}