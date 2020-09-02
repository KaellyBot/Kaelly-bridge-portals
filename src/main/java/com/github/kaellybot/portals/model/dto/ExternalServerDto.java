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
@JsonDeserialize(builder = ExternalServerDto.ExternalServerDtoBuilder.class)
@Builder(builderClassName = "ExternalServerDtoBuilder", toBuilder = true)
public class ExternalServerDto {

    @NotBlank(message = PortalConstants.ID_NOT_FOUND_MESSAGE) String id;
    @NotBlank(message = "The image is mandatory.") String image;
    @Multilingual Map<Language, String> labels;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ExternalServerDtoBuilder {}
}