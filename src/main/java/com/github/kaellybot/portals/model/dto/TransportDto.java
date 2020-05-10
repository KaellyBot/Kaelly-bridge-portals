package com.github.kaellybot.portals.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize(builder = TransportDto.TransportDtoBuilder.class)
@Builder(builderClassName = "TransportDtoBuilder", toBuilder = true)
public class TransportDto {
    String type;
    String area;
    String subArea;
    PositionDto position;
    boolean isAvailableUnderConditions;

    @JsonPOJOBuilder(withPrefix = "")
    public static class TransportDtoBuilder {}
}