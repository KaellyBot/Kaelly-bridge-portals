package com.github.kaellybot.portals.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorDto {

    private String name;
    private String platform;
}