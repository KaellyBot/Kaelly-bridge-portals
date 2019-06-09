package com.github.kaellybot.portals.model.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PortalId implements Serializable {

    private String server;
    private String dimension;
}
