package com.github.kaellybot.portals.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.github.kaellybot.portals.controller.PortalConstants;
import com.github.kaellybot.portals.model.constants.Privilege;
import com.github.kaellybot.portals.model.constants.UserType;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Value
@JsonDeserialize(builder = ExternalTokenDto.TokenDtoBuilder.class)
@Builder(builderClassName = "TokenDtoBuilder", toBuilder = true)
public class ExternalTokenDto {
    @NotBlank(message = PortalConstants.USERNAME_NOT_FOUND_MESSAGE) String username;
    @NotBlank(message = PortalConstants.PASSWORD_NOT_FOUND_MESSAGE) String password;
    @NotNull(message = PortalConstants.USER_TYPE_NOT_FOUND_MESSAGE) UserType userType;
    @NotEmpty(message = PortalConstants.PRIVILEGE_NOT_FOUND_MESSAGE) List<Privilege> privileges;

    @JsonPOJOBuilder(withPrefix = "")
    public static class TokenDtoBuilder {}
}