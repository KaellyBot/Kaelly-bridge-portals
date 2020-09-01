package com.github.kaellybot.portals.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize(builder = ServerDto.ServerDtoBuilder.class)
@Builder(builderClassName = "ServerDtoBuilder", toBuilder = true)
public class ServerDto {

    String id;
    String name;
    String image;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ServerDtoBuilder {}
}