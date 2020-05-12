package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.constants.Language;
import com.github.kaellybot.portals.model.dto.ExternalPortalDto;
import com.github.kaellybot.portals.model.dto.PortalDto;
import com.github.kaellybot.portals.model.entity.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PortalMapper {

    private final PositionMapper positionMapper;

    private final AuthorMapper authorMapper;

    private final TransportMapper transportMapper;

    public PortalDto map(Portal portal, Server server, Dimension dimension, Language language) {
        PortalDto.PortalDtoBuilder result = PortalDto.builder()
                .server(Optional.ofNullable(server.getTranslation()).map(map -> map.get(language)).orElse(server.getId()))
                .dimension(Optional.ofNullable(dimension.getTranslation()).map(map -> map.get(language)).orElse(dimension.getId()))
                .isAvailable(portal.isAvailable());

        if (portal.isValid()) {
            result.position(positionMapper.map(portal.getPosition()))
                    .utilisation(portal.getUtilisation())
                    .creationDate(portal.getCreationDate())
                    .creationAuthor(authorMapper.map(portal.getCreationAuthor()))
                    .nearestZaap(transportMapper.map(portal.getNearestZaap(), language));

            if (portal.isUpdated())
                result.lastUpdateDate(portal.getLastUpdateDate())
                        .lastAuthorUpdate(authorMapper.map(portal.getLastAuthorUpdate()));

            if (portal.isTransportLimitedNearest())
                result.nearestTransportLimited(transportMapper.map(portal.getNearestTransportLimited(), language));
        }

        return result.build();
    }

    public Portal map(Server server, Dimension dimension, ExternalPortalDto externalPortalDto){
        return Portal.builder()
                .portalId(PortalId.builder().serverId(server.getId()).dimensionId(dimension.getId()).build())
                .position(positionMapper.map(externalPortalDto.getPosition()))
                .utilisation(externalPortalDto.getUtilisation())
                .creationDate(Instant.now())
                .creationAuthor(Author.builder().name(externalPortalDto.getAuthor()).build())
                .lastUpdateDate(Instant.now())
                .lastAuthorUpdate(Author.builder().name(externalPortalDto.getAuthor()).build())
                .build();
    }
}