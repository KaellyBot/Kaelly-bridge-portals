package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.dto.PositionDto;
import com.github.kaellybot.portals.model.entity.Position;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PositionMapper {

    public PositionDto map(Position position){
        return PositionDto.builder()
                .x(position.getX())
                .y(position.getY())
                .build();
    }

    public Position map(PositionDto position){
        return Position.builder()
                .x(position.getX())
                .y(position.getY())
                .build();
    }
}