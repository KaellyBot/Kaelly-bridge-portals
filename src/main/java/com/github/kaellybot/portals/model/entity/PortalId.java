package com.github.kaellybot.portals.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PortalId implements Serializable {

    private String server;
    private String dimension;
}
