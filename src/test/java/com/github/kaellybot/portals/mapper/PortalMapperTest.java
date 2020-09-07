package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.commons.model.entity.Dimension;
import com.github.kaellybot.commons.model.entity.Server;
import com.github.kaellybot.portals.model.constants.Transport;
import com.github.kaellybot.portals.model.dto.ExternalPortalDto;
import com.github.kaellybot.portals.model.dto.PortalDto;
import com.github.kaellybot.portals.model.dto.PositionDto;
import com.github.kaellybot.portals.model.entity.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static com.github.kaellybot.portals.controller.PortalConstants.DEFAULT_LANGUAGE;
import static com.github.kaellybot.portals.model.entity.Portal.PORTAL_LIFETIME_IN_DAYS;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PortalMapperTest {

    private static final String DEFAULT_SERVER_LABEL = "DEFAULT_SERVER_LABEL";
    private static final String DEFAULT_DIMENSION_LABEL = "DEFAULT_DIMENSION_LABEL";

    private static final Server DEFAULT_SERVER = Server.builder().id("DEFAULT_SERVER")
            .labels(Map.of(DEFAULT_LANGUAGE, DEFAULT_SERVER_LABEL)).build();
    private static final Dimension DEFAULT_DIMENSION = Dimension.builder().id("DEFAULT_DIMENSION")
            .labels(Map.of(DEFAULT_LANGUAGE, DEFAULT_DIMENSION_LABEL)).build();


    
    private static final Instant FRESH = Instant.now();
    private static final Instant OLD = FRESH.minus(PORTAL_LIFETIME_IN_DAYS + 1, ChronoUnit.DAYS);
    // Platform
    private static final String DISCORD = "Discord";
    private static final String DOFUS_PORTALS = "Dofus-portals";
    private static final String DIMTOPIA = "Dimtopia";

    // Persona
    private static final String OSGL = "Osgl";
    private static final String CHIRON = "Chiron";
    private static final String BLANCIX = "Blancix";
    private static final String KIZARD = "Kizard";
    private static final String GRABUGE = "Grabuge";
    private static final String SONGFU = "Songfu";

    @Autowired private PortalMapper portalMapper;
    @Autowired private PositionMapper positionMapper;
    @Autowired private ServerMapper serverMapper;
    @Autowired private DimensionMapper dimensionMapper;
    @Autowired private TransportMapper transportMapper;
    @Autowired private AuthorMapper authorMapper;

    @ParameterizedTest
    @MethodSource("getPortals")
    void mapPortalDtoTest(Portal portal)
    {
        PortalDto result = portalMapper.map(portal, DEFAULT_SERVER, DEFAULT_DIMENSION, DEFAULT_LANGUAGE);
        assertThat(result).isNotNull();
        assertThat(result.getServer())
                .isNotNull().isEqualTo(serverMapper.map(DEFAULT_SERVER, DEFAULT_LANGUAGE));
        assertThat(result.getDimension())
                .isNotNull().isEqualTo(dimensionMapper.map(DEFAULT_DIMENSION, DEFAULT_LANGUAGE));
        assertThat(result.getIsAvailable())
                .isNotNull().isEqualTo(Optional.ofNullable(portal.getIsAvailable()).orElse(false));

        if (portal.isValid()){
            assertThat(result.getPosition()).isNotNull();
            assertThat(result.getPosition())
                    .isNotNull().isEqualTo(positionMapper.map(portal.getPosition()));
            assertThat(result.getUtilisation())
                    .isEqualTo(portal.getUtilisation());
            assertThat(result.getCreationDate())
                    .isNotNull().isEqualTo(portal.getCreationDate());
            assertThat(result.getNearestZaap()).isNotNull()
                    .isEqualTo(transportMapper.map(portal.getNearestZaap(), DEFAULT_LANGUAGE));

            if (Optional.ofNullable(portal.getIsUpdated()).orElse(false)){
                assertThat(result
                        .getLastUpdateDate()).isNotNull().isEqualTo(portal.getLastUpdateDate());
                assertThat(result
                        .getLastAuthorUpdate()).isNotNull().isEqualTo(authorMapper.map(portal.getLastAuthorUpdate()));
            }
            else {
                assertThat(result.getLastUpdateDate()).isNull();
                assertThat(result.getLastAuthorUpdate()).isNull();
            }

            if (Optional.ofNullable(portal.getTransportLimitedNearest()).orElse(false))
                assertThat(result
                        .getNearestTransportLimited()).isNotNull()
                        .isEqualTo(transportMapper.map(portal.getNearestTransportLimited(), DEFAULT_LANGUAGE));
            else
                assertThat(result
                        .getNearestTransportLimited()).isNull();
        }
        else {
            assertThat(result.getPosition()).isNull();
            assertThat(result.getPosition()).isNull();
            assertThat(result.getUtilisation()).isNull();
            assertThat(result.getCreationDate()).isNull();
            assertThat(result.getLastUpdateDate()).isNull();
            assertThat(result.getLastAuthorUpdate()).isNull();
            assertThat(result.getNearestZaap()).isNull();
            assertThat(result.getNearestTransportLimited()).isNull();
        }
    }

    @ParameterizedTest
    @MethodSource("getExternalPortalsDto")
    void mapExternalPortalDtoTest(ExternalPortalDto portal)
    {
        Instant before = Instant.now();
        Portal result = portalMapper.map(DEFAULT_SERVER, DEFAULT_DIMENSION, portal);
        Instant after = Instant.now();

        assertThat(result).isNotNull();
        assertThat(result.getPortalId()).isNotNull();
        assertThat(result.getPortalId().getServerId()).isNotNull().isEqualTo(DEFAULT_SERVER.getId());
        assertThat(result.getPortalId().getDimensionId()).isNotNull().isEqualTo(DEFAULT_DIMENSION.getId());
        assertThat(result.getPosition()).isNotNull();
        assertThat(result.getPosition().getX()).isNotNull().isEqualTo(portal.getPosition().getX());
        assertThat(result.getPosition().getY()).isNotNull().isEqualTo(portal.getPosition().getY());
        assertThat(result.getUtilisation()).isEqualTo(portal.getUtilisation());
        assertThat(result.getCreationDate()).isBetween(before, after);
        assertThat(result.getLastUpdateDate()).isBetween(before, after);
        assertThat(result.getCreationAuthor()).isNotNull();
        assertThat(result.getCreationAuthor().getName()).isEqualTo(portal.getAuthor());
        assertThat(result.getLastAuthorUpdate()).isNotNull();
        assertThat(result.getLastAuthorUpdate().getName()).isEqualTo(portal.getAuthor());
    }

    private static Stream<Portal> getPortals() {
        return Stream.of(
                // No available portals
                Portal.builder()
                        .portalId(PortalId.builder().dimensionId(DEFAULT_DIMENSION.getId()).serverId(DEFAULT_SERVER.getId()).build())
                        .isAvailable(true)
                        .creationDate(OLD)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimensionId(DEFAULT_DIMENSION.getId()).serverId(DEFAULT_SERVER.getId()).build())
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimensionId(DEFAULT_DIMENSION.getId()).serverId(DEFAULT_SERVER.getId()).build())
                        .isAvailable(true)
                        .creationDate(OLD).creationAuthor(Author.builder().name(OSGL).platform(DISCORD).build())
                        .nearestZaap(Transport.ZAAP_PORT_MADRESTAM)
                        .nearestTransportLimited(Transport.FOREUSE_MINE_ISTAIRAMEUR)
                        .build(),

                // Available portals
                Portal.builder()
                        .portalId(PortalId.builder().dimensionId(DEFAULT_DIMENSION.getId()).serverId(DEFAULT_SERVER.getId()).build())
                        .isAvailable(true)
                        .position(Position.builder().x(2).y(0).build())
                        .creationDate(FRESH).creationAuthor(Author.builder().name(BLANCIX).platform(DISCORD).build())
                        .nearestZaap(Transport.ZAAP_BERCEAU)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimensionId(DEFAULT_DIMENSION.getId()).serverId(DEFAULT_SERVER.getId()).build())
                        .isAvailable(true)
                        .position(Position.builder().x(5).y(7).build())
                        .creationDate(FRESH).creationAuthor(Author.builder().name(GRABUGE).platform(DISCORD).build())
                        .lastUpdateDate(FRESH).lastAuthorUpdate(Author.builder().name(CHIRON).platform(DOFUS_PORTALS).build())
                        .nearestZaap(Transport.FOREUSE_KARTONPATH).transportLimitedNearest(true)
                        .nearestTransportLimited(Transport.BRIGANDIN_RIVIERE_KAWAII)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimensionId(DEFAULT_DIMENSION.getId()).serverId(DEFAULT_SERVER.getId()).build())
                        .isAvailable(true)
                        .position(Position.builder().x(-666).y(-666).build())
                        .creationDate(FRESH).creationAuthor(Author.builder().name(SONGFU).platform(DISCORD).build())
                        .isUpdated(true).lastUpdateDate(FRESH)
                        .lastAuthorUpdate(Author.builder().name(OSGL).platform(DOFUS_PORTALS).build())
                        .nearestZaap(Transport.ZAAP_CITE_ASTRUB)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimensionId(DEFAULT_DIMENSION.getId()).serverId(DEFAULT_SERVER.getId()).build())
                        .isAvailable(true)
                        .position(Position.builder().x(-1).y(1).build())
                        .creationDate(FRESH).creationAuthor(Author.builder().name(KIZARD).platform(DISCORD).build())
                        .isUpdated(true).lastUpdateDate(FRESH)
                        .lastAuthorUpdate(Author.builder().name(CHIRON).platform(DIMTOPIA).build())
                        .nearestZaap(Transport.ZAAP_ILE_CAWOTTE).transportLimitedNearest(true)
                        .nearestTransportLimited(Transport.BRIGANDIN_ILE_MINOTOROR)
                        .build()
        );
    }

    private static Stream<ExternalPortalDto> getExternalPortalsDto(){
        return Stream.of(ExternalPortalDto.builder()
                .author(OSGL)
                .position(PositionDto.builder().x(1).y(1).build())
                .utilisation(1)
                .build(),
                ExternalPortalDto.builder()
                        .position(PositionDto.builder().x(1).y(1).build())
                        .utilisation(1)
                        .build(),
                ExternalPortalDto.builder()
                        .position(PositionDto.builder().x(1).y(1).build())
                        .build()
        );
    }
}