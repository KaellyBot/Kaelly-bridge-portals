package com.github.kaellybot.portals.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Author {

    private String name;
    private String platform;
}
