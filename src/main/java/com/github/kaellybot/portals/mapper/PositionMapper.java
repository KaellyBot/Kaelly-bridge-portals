package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.dto.PositionDto;
import com.github.kaellybot.portals.model.entity.Position;

public class PositionMapper {

    public static PositionDto map(Position position){
        return new PositionDto()
                .withX(position.getX())
                .withY(position.getY());
    }
}
