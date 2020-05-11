package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.constants.Transport;
import com.github.kaellybot.portals.model.entity.Author;
import com.github.kaellybot.portals.model.entity.Portal;
import com.github.kaellybot.portals.model.entity.PortalId;
import com.github.kaellybot.portals.model.entity.Position;
import com.github.kaellybot.portals.util.Translator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import static com.github.kaellybot.portals.controller.PortalConstants.DEFAULT_LANGUAGE;
import static com.github.kaellybot.portals.model.constants.Dimension.*;
import static com.github.kaellybot.portals.model.entity.Portal.PORTAL_LIFETIME_IN_DAYS;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PortalMapperTest {

    private static final String DEFAULT_SERVER = "DEFAULT_SERVER";

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
    @Autowired private TransportMapper transportMapper;
    @Autowired private AuthorMapper authorMapper;
    @Autowired private Translator translator;

    @ParameterizedTest
    @MethodSource("getPortals")
    void mapPortalDtoTest(Portal portal)
    {
        assertThat(portalMapper.map(portal, DEFAULT_LANGUAGE)).isNotNull();
        assertThat(translator.getLabel(DEFAULT_LANGUAGE, portal.getPortalId().getDimension())).isNotNull()
                .isEqualTo(portalMapper.map(portal, DEFAULT_LANGUAGE).getDimension());
        assertThat(portal.isAvailable()).isNotNull()
                .isEqualTo(portalMapper.map(portal, DEFAULT_LANGUAGE).getIsAvailable());

        if (portal.isValid()){
            assertThat(portalMapper.map(portal, DEFAULT_LANGUAGE).getPosition()).isNotNull();
            assertThat(positionMapper.map(portal.getPosition())).isNotNull()
                    .isEqualTo(portalMapper.map(portal, DEFAULT_LANGUAGE).getPosition());
            assertThat(portal.getUtilisation())
                    .isEqualTo(portalMapper.map(portal, DEFAULT_LANGUAGE).getUtilisation());
            assertThat(portal.getCreationDate()).isNotNull()
                    .isEqualTo(portalMapper.map(portal, DEFAULT_LANGUAGE).getCreationDate());
            assertThat(transportMapper.map(portal.getNearestZaap(), DEFAULT_LANGUAGE)).isNotNull()
                    .isEqualTo(portalMapper.map(portal, DEFAULT_LANGUAGE).getNearestZaap());

            if (portal.isUpdated()){
                assertThat(portal.getLastUpdateDate()).isNotNull()
                        .isEqualTo(portalMapper.map(portal, DEFAULT_LANGUAGE).getLastUpdateDate());
                assertThat(authorMapper.map(portal.getLastAuthorUpdate())).isNotNull()
                        .isEqualTo(portalMapper.map(portal, DEFAULT_LANGUAGE).getLastAuthorUpdate());
            }
            else {
                assertThat(portalMapper.map(portal, DEFAULT_LANGUAGE).getLastUpdateDate()).isNull();
                assertThat(portalMapper.map(portal, DEFAULT_LANGUAGE).getLastAuthorUpdate()).isNull();
            }

            if (portal.isTransportLimitedNearest())
                assertThat(transportMapper.map(portal.getNearestTransportLimited(), DEFAULT_LANGUAGE)).isNotNull()
                        .isEqualTo(portalMapper.map(portal, DEFAULT_LANGUAGE).getNearestTransportLimited());
            else
                assertThat(portalMapper.map(portal, DEFAULT_LANGUAGE).getNearestTransportLimited()).isNull();
        }
        else {
            assertThat(portalMapper.map(portal, DEFAULT_LANGUAGE).getPosition()).isNull();
            assertThat(portalMapper.map(portal, DEFAULT_LANGUAGE).getPosition()).isNull();
            assertThat(portalMapper.map(portal, DEFAULT_LANGUAGE).getUtilisation()).isNull();
            assertThat(portalMapper.map(portal, DEFAULT_LANGUAGE).getCreationDate()).isNull();
            assertThat(portalMapper.map(portal, DEFAULT_LANGUAGE).getLastUpdateDate()).isNull();
            assertThat(portalMapper.map(portal, DEFAULT_LANGUAGE).getLastAuthorUpdate()).isNull();
            assertThat(portalMapper.map(portal, DEFAULT_LANGUAGE).getNearestZaap()).isNull();
            assertThat(portalMapper.map(portal, DEFAULT_LANGUAGE).getNearestTransportLimited()).isNull();
        }
    }

    private static Stream<Portal> getPortals() {
        return Stream.of(
                // No available portals
                Portal.builder()
                        .portalId(PortalId.builder().dimension(SRAMBAD).serverId(DEFAULT_SERVER).build())
                        .isAvailable(true)
                        .creationDate(OLD)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ENUTROSOR).serverId(DEFAULT_SERVER).build())
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimension(XELORIUM).serverId(DEFAULT_SERVER).build())
                        .isAvailable(true)
                        .creationDate(OLD).creationAuthor(Author.builder().name(OSGL).platform(DISCORD).build())
                        .nearestZaap(Transport.ZAAP_PORT_MADRESTAM)
                        .nearestTransportLimited(Transport.FOREUSE_MINE_ISTAIRAMEUR)
                        .build(),

                // Available portals
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ENUTROSOR).serverId(DEFAULT_SERVER).build())
                        .isAvailable(true)
                        .position(Position.builder().x(2).y(0).build())
                        .creationDate(FRESH).creationAuthor(Author.builder().name(BLANCIX).platform(DISCORD).build())
                        .nearestZaap(Transport.ZAAP_BERCEAU)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ENUTROSOR).serverId(DEFAULT_SERVER).build())
                        .isAvailable(true)
                        .position(Position.builder().x(5).y(7).build())
                        .creationDate(FRESH).creationAuthor(Author.builder().name(GRABUGE).platform(DISCORD).build())
                        .lastUpdateDate(FRESH).lastAuthorUpdate(Author.builder().name(CHIRON).platform(DOFUS_PORTALS).build())
                        .nearestZaap(Transport.FOREUSE_KARTONPATH).transportLimitedNearest(true)
                        .nearestTransportLimited(Transport.BRIGANDIN_RIVIERE_KAWAII)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ECAFLIPUS).serverId(DEFAULT_SERVER).build())
                        .isAvailable(true)
                        .position(Position.builder().x(-666).y(-666).build())
                        .creationDate(FRESH).creationAuthor(Author.builder().name(SONGFU).platform(DISCORD).build())
                        .isUpdated(true).lastUpdateDate(FRESH)
                        .lastAuthorUpdate(Author.builder().name(OSGL).platform(DOFUS_PORTALS).build())
                        .nearestZaap(Transport.ZAAP_CITE_ASTRUB)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ENUTROSOR).serverId(DEFAULT_SERVER).build())
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
}