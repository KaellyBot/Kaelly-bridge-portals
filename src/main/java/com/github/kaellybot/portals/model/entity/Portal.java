package com.github.kaellybot.portals.model.entity;

import com.github.kaellybot.portals.model.constants.Transport;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "portals")
public class Portal {

    @Id
    private PortalId portalId;
    private Position position;
    private Integer utilisation;
    private Instant creationDate;
    private Author creationAuthor;
    private Instant lastUpdateDate;
    private Author lastAuthorUpdate;
    private Transport nearestZaap;
    private Transport nearestTransportLimited;
    private boolean transportLimitedNearest;
    private boolean isAvailable;
    private boolean isUpdated;
}