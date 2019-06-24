package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.dto.PortalDto;
import com.github.kaellybot.portals.model.entity.Portal;

import java.time.Duration;
import java.time.Instant;

public final class PortalMapper {

    static final long PORTAL_LIFETIME_IN_DAYS = 2;

    private PortalMapper(){}

    public static PortalDto map(Portal portal) {
        PortalDto.PortalDtoBuilder result = PortalDto.builder()
                .dimension(portal.getPortalId().getDimension().name())
                .isAvailable(portal.isAvailable());

        if (isPortalStillFresh(portal)) {
            result.position(PositionMapper.map(portal.getPosition()))
                    .utilisation(portal.getUtilisation())
                    .creationDate(portal.getCreationDate())
                    .creationAuthor(AuthorMapper.map(portal.getCreationAuthor()))
                    .nearestZaap(TransportMapper.map(portal.getNearestZaap()));

            if (portal.isUpdated())
                result.lastUpdateDate(portal.getLastUpdateDate())
                        .lastAuthorUpdate(AuthorMapper.map(portal.getLastAuthorUpdate()));

            if (portal.isTransportLimitedNearest())
                result.nearestTransportLimited(TransportMapper.map(portal.getNearestTransportLimited()));
        }

        return result.build();
    }

    private static boolean isPortalStillFresh(Portal portal){
        return portal.isAvailable() &&
                Math.abs(Duration.between(Instant.now(), portal.getCreationDate()).toDays()) < PORTAL_LIFETIME_IN_DAYS;
    }
}