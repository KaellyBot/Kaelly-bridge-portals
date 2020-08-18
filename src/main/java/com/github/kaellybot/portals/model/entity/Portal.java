package com.github.kaellybot.portals.model.entity;

import com.github.kaellybot.portals.model.constants.Transport;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.time.Instant;

@Data
@Builder
@Document(collection = "portals")
public class Portal {

    public static final long PORTAL_LIFETIME_IN_DAYS = 2;

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
    private Boolean transportLimitedNearest;
    private Boolean isAvailable;
    private Boolean isUpdated;

    public boolean isValid(){
        return getIsAvailable() && Math.abs(Duration.
                between(Instant.now(), getCreationDate()).toDays()) < PORTAL_LIFETIME_IN_DAYS;
    }

    public void merge(Portal portal){
        if (getPortalId().equals(portal.getPortalId()) && portal.getPosition() != null) {
            if (portal.getPosition().equals(getPosition()) &&
                    (getUtilisation() == null || getUtilisation() > portal.getUtilisation())) {
                setIsUpdated(true);
                setUtilisation(portal.getUtilisation());
                setLastUpdateDate(portal.getLastUpdateDate());
                setLastAuthorUpdate(portal.getLastAuthorUpdate());
            } else {
                if (getPosition() == null || getPosition() != null && !getPosition().equals(portal.getPosition())
                        && getCreationDate().toEpochMilli() < portal.getCreationDate().toEpochMilli()) {
                    setIsAvailable(true);
                    setPosition(portal.getPosition());
                    setCreationDate(portal.getCreationDate());
                    setCreationAuthor(portal.getCreationAuthor());
                    setUtilisation(portal.getUtilisation());
                    setIsUpdated(false);
                    setLastUpdateDate(null);
                    setLastAuthorUpdate(null);
                    determineTransports();
                }
            }
        }
    }

    void determineTransports() {
        double minDist = Double.MAX_VALUE;
        double minDistLimited = Double.MAX_VALUE;
        for (Transport transport : Transport.values()) {
            double distance = transport.getPosition().getDistance(getPosition());
            if (transport.isAvailableUnderConditions() &&
                    (getNearestZaap() == null || minDist > distance)){
                setNearestZaap(transport);
                minDist = distance;
            }
            if (! transport.isAvailableUnderConditions() && (getNearestTransportLimited() == null
                    || minDistLimited > distance)){
                setNearestTransportLimited(transport);
                setTransportLimitedNearest(true);
                minDistLimited = distance;
            }
        }

        if (minDist < minDistLimited) {
            setNearestTransportLimited(null);
            setTransportLimitedNearest(false);
        }
    }
}