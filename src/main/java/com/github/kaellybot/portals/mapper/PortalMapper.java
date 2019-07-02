package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.constants.Dimension;
import com.github.kaellybot.portals.model.constants.Language;
import com.github.kaellybot.portals.model.constants.Server;
import com.github.kaellybot.portals.model.dto.ExternalPortalDto;
import com.github.kaellybot.portals.model.dto.PortalDto;
import com.github.kaellybot.portals.model.entity.Author;
import com.github.kaellybot.portals.model.entity.Portal;
import com.github.kaellybot.portals.model.entity.PortalId;

import java.time.Instant;

public final class PortalMapper {

    private PortalMapper(){}

    public static PortalDto map(Portal portal, Language language) {
        PortalDto.PortalDtoBuilder result = PortalDto.builder()
                .dimension(portal.getPortalId().getDimension().getLabel(language))
                .isAvailable(portal.isAvailable());

        if (portal.isValid()) {
            result.position(PositionMapper.map(portal.getPosition()))
                    .utilisation(portal.getUtilisation())
                    .creationDate(portal.getCreationDate())
                    .creationAuthor(AuthorMapper.map(portal.getCreationAuthor()))
                    .nearestZaap(TransportMapper.map(portal.getNearestZaap(), language));

            if (portal.isUpdated())
                result.lastUpdateDate(portal.getLastUpdateDate())
                        .lastAuthorUpdate(AuthorMapper.map(portal.getLastAuthorUpdate()));

            if (portal.isTransportLimitedNearest())
                result.nearestTransportLimited(TransportMapper.map(portal.getNearestTransportLimited(), language));
        }

        return result.build();
    }

    public static Portal map(Server server, Dimension dimension, ExternalPortalDto externalPortalDto){
        return Portal.builder()
                .portalId(PortalId.builder().server(server).dimension(dimension).build())
                .position(PositionMapper.map(externalPortalDto.getPosition()))
                .utilisation(externalPortalDto.getUtilisation())
                .creationDate(Instant.now())
                .creationAuthor(Author.builder().name(externalPortalDto.getAuthor()).build())
                .lastUpdateDate(Instant.now())
                .lastAuthorUpdate(Author.builder().name(externalPortalDto.getAuthor()).build())
                .build();
    }
}