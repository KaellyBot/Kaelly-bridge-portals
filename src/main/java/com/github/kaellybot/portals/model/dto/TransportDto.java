package com.github.kaellybot.portals.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransportDto {
    private String type;
    private String area;
    private String subArea;
    private PositionDto position;
    private boolean isAvailableUnderConditions;
}