package com.github.kaellybot.portals.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize(builder = ExternalPortalDto.ExternalPortalDtoBuilder.class)
@Builder(builderClassName = "ExternalPortalDtoBuilder", toBuilder = true)
public class ExternalPortalDto {

    private PositionDto position;
    private int utilisation;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ExternalPortalDtoBuilder {}
}
