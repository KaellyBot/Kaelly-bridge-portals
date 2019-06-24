package com.github.kaellybot.portals.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize(builder = PositionDto.PositionDtoBuilder.class)
@Builder(builderClassName = "PositionDtoBuilder", toBuilder = true)
public class PositionDto {

    private int x;
    private int y;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PositionDtoBuilder {}
}
