package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.commons.model.constants.Language;
import com.github.kaellybot.commons.model.entity.Dimension;
import com.github.kaellybot.commons.model.entity.Server;
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

    private final ServerMapper serverMapper;

    private final DimensionMapper dimensionMapper;

    private final PositionMapper positionMapper;

    private final AuthorMapper authorMapper;

    private final TransportMapper transportMapper;

    public PortalDto map(Portal portal, Server server, Dimension dimension, Language language) {
        PortalDto.PortalDtoBuilder result = PortalDto.builder()
                .server(serverMapper.map(server, language))
                .dimension(dimensionMapper.map(dimension, language))
                .isAvailable(Optional.ofNullable(portal.getIsAvailable()).orElse(false));

        if (portal.isValid()) {
            result.position(positionMapper.map(portal.getPosition()))
                    .utilisation(portal.getUtilisation())
                    .creationDate(portal.getCreationDate())
                    .creationAuthor(authorMapper.map(portal.getCreationAuthor()))
                    .nearestZaap(transportMapper.map(portal.getNearestZaap(), language));

            if (Optional.ofNullable(portal.getIsUpdated()).orElse(false))
                result.lastUpdateDate(portal.getLastUpdateDate())
                        .lastAuthorUpdate(authorMapper.map(portal.getLastAuthorUpdate()));

            if (Optional.ofNullable(portal.getTransportLimitedNearest()).orElse(false))
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