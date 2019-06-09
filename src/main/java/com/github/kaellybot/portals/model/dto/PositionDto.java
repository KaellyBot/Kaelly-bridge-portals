package com.github.kaellybot.portals.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PositionDto {

    private int x;
    private int y;
}
