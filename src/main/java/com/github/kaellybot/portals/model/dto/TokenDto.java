package com.github.kaellybot.portals.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.github.kaellybot.portals.model.constants.Privilege;
import com.github.kaellybot.portals.model.constants.UserType;
import lombok.Builder;
import lombok.Value;

import java.util.List;


@Value
@JsonDeserialize(builder = TokenDto.TokenDtoBuilder.class)
@Builder(builderClassName = "TokenDtoBuilder", toBuilder = true)
public class TokenDto {

    String id;
    String username;
    UserType userType;
    List<Privilege> privileges;

    @JsonPOJOBuilder(withPrefix = "")
    public static class TokenDtoBuilder {}
}