package com.github.kaellybot.portals.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.github.kaellybot.commons.model.constants.Language;
import com.github.kaellybot.portals.controller.PortalConstants;
import com.github.kaellybot.portals.util.Multilingual;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Value
@JsonDeserialize(builder = ExternalDimensionDto.ExternalDimensionDtoBuilder.class)
@Builder(builderClassName = "ExternalDimensionDtoBuilder", toBuilder = true)
public class ExternalDimensionDto {

    @NotBlank(message = PortalConstants.ID_NOT_FOUND_MESSAGE) String id;
    @NotBlank(message = PortalConstants.IMAGE_NOT_FOUND_MESSAGE) String image;
    @Multilingual Map<Language, String> labels;
    int color;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ExternalDimensionDtoBuilder {}
}