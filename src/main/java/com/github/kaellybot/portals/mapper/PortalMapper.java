package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.constants.Dimension;
import com.github.kaellybot.portals.model.constants.Language;
import com.github.kaellybot.portals.model.dto.ExternalPortalDto;
import com.github.kaellybot.portals.model.dto.PortalDto;
import com.github.kaellybot.portals.model.entity.Author;
import com.github.kaellybot.portals.model.entity.Portal;
import com.github.kaellybot.portals.model.entity.PortalId;
import com.github.kaellybot.portals.model.entity.Server;
import com.github.kaellybot.portals.util.Translator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
public class PortalMapper {

    private Translator translator;

    private PositionMapper positionMapper;

    private AuthorMapper authorMapper;

    private TransportMapper transportMapper;

    public PortalDto map(Portal portal, Language language) {
        PortalDto.PortalDtoBuilder result = PortalDto.builder()
                .dimension(translator.getLabel(language, portal.getPortalId().getDimension()))
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
                .portalId(PortalId.builder().serverId(server.getId()).dimension(dimension).build())
                .position(positionMapper.map(externalPortalDto.getPosition()))
                .utilisation(externalPortalDto.getUtilisation())
                .creationDate(Instant.now())
                .creationAuthor(Author.builder().name(externalPortalDto.getAuthor()).build())
                .lastUpdateDate(Instant.now())
                .lastAuthorUpdate(Author.builder().name(externalPortalDto.getAuthor()).build())
                .build();
    }
}