package com.github.kaellybot.portals.model.entity;

import com.github.kaellybot.portals.model.constants.Dimension;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PortalId implements Serializable {

    private String serverId;
    private Dimension dimension;
}
