package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.dto.PositionDto;
import com.github.kaellybot.portals.model.entity.Position;

public final class PositionMapper {

    private PositionMapper(){}

    public static PositionDto map(Position position){
        return PositionDto.builder()
                .x(position.getX())
                .y(position.getY())
                .build();
    }

    public static Position map(PositionDto position){
        return Position.builder()
                .x(position.getX())
                .y(position.getY())
                .build();
    }
}