package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.constants.Transport;
import com.github.kaellybot.portals.model.entity.Author;
import com.github.kaellybot.portals.model.entity.Portal;
import com.github.kaellybot.portals.model.entity.PortalId;
import com.github.kaellybot.portals.model.entity.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import static com.github.kaellybot.portals.controller.PortalConstants.DEFAULT_LANGUAGE;
import static com.github.kaellybot.portals.mapper.PortalMapper.PORTAL_LIFETIME_IN_DAYS;
import static com.github.kaellybot.portals.model.constants.Dimension.*;
import static com.github.kaellybot.portals.model.constants.Server.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PortalMapperTest {

    private final static Instant FRESH = Instant.now();
    private final static Instant OLD = FRESH.minus(PORTAL_LIFETIME_IN_DAYS + 1, ChronoUnit.DAYS);
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

    @ParameterizedTest
    @MethodSource("getPortals")
    void mapPortalDtoTest(Portal portal)
    {
        assertNotNull(PortalMapper.map(portal, DEFAULT_LANGUAGE));
        assertEquals(portal.getPortalId().getDimension().getLabel(DEFAULT_LANGUAGE),
                PortalMapper.map(portal, DEFAULT_LANGUAGE).getDimension());
        assertEquals(portal.isAvailable(), PortalMapper.map(portal, DEFAULT_LANGUAGE).getIsAvailable());

        if (PortalMapper.isPortalStillFresh(portal)){
            assertNotNull(PortalMapper.map(portal, DEFAULT_LANGUAGE).getPosition());
            assertEquals(PositionMapper.map(portal.getPosition()),
                    PortalMapper.map(portal, DEFAULT_LANGUAGE).getPosition());
            assertEquals(portal.getUtilisation(), PortalMapper.map(portal, DEFAULT_LANGUAGE).getUtilisation());
            assertEquals(portal.getCreationDate(), PortalMapper.map(portal, DEFAULT_LANGUAGE).getCreationDate());
            assertEquals(TransportMapper.map(portal.getNearestZaap(), DEFAULT_LANGUAGE),
                    PortalMapper.map(portal, DEFAULT_LANGUAGE).getNearestZaap());

            if (portal.isUpdated()){
                assertEquals(portal.getLastUpdateDate(), PortalMapper.map(portal, DEFAULT_LANGUAGE).getLastUpdateDate());
                assertEquals(AuthorMapper.map(portal.getLastAuthorUpdate()),
                        PortalMapper.map(portal, DEFAULT_LANGUAGE).getLastAuthorUpdate());
            }
            else {
                assertNull(PortalMapper.map(portal, DEFAULT_LANGUAGE).getLastUpdateDate());
                assertNull(PortalMapper.map(portal, DEFAULT_LANGUAGE).getLastAuthorUpdate());
            }

            if (portal.isTransportLimitedNearest())
                assertEquals(TransportMapper.map(portal.getNearestTransportLimited(), DEFAULT_LANGUAGE),
                        PortalMapper.map(portal, DEFAULT_LANGUAGE).getNearestTransportLimited());
            else
                assertNull(PortalMapper.map(portal, DEFAULT_LANGUAGE).getNearestTransportLimited());
        }
        else {
            assertNull(PortalMapper.map(portal, DEFAULT_LANGUAGE).getPosition());
            assertNull(PortalMapper.map(portal, DEFAULT_LANGUAGE).getPosition());
            assertNull(PortalMapper.map(portal, DEFAULT_LANGUAGE).getUtilisation());
            assertNull(PortalMapper.map(portal, DEFAULT_LANGUAGE).getCreationDate());
            assertNull(PortalMapper.map(portal, DEFAULT_LANGUAGE).getLastUpdateDate());
            assertNull(PortalMapper.map(portal, DEFAULT_LANGUAGE).getLastAuthorUpdate());
            assertNull(PortalMapper.map(portal, DEFAULT_LANGUAGE).getNearestZaap());
            assertNull(PortalMapper.map(portal, DEFAULT_LANGUAGE).getNearestTransportLimited());
        }
    }

    private static Stream<Portal> getPortals() {
        return Stream.of(
                // No available portals
                Portal.builder()
                        .portalId(PortalId.builder().dimension(SRAMBAD).server(FURYE).build())
                        .isAvailable(true)
                        .creationDate(OLD)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ENUTROSOR).server(FURYE).build())
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimension(XELORIUM).server(CROCABULIA).build())
                        .isAvailable(true)
                        .creationDate(OLD).creationAuthor(Author.builder().name(OSGL).platform(DISCORD).build())
                        .nearestZaap(Transport.BERCEAU)
                        .nearestTransportLimited(Transport.FOREUSE_MINE_ISTAIRAMEUR)
                        .build(),

                // Available portals
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ENUTROSOR).server(JULITH).build())
                        .isAvailable(true)
                        .position(Position.builder().x(2).y(0).build())
                        .creationDate(FRESH).creationAuthor(Author.builder().name(BLANCIX).platform(DISCORD).build())
                        .nearestZaap(Transport.CITE_D_ASTRUB)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ENUTROSOR).server(ATCHAM).build())
                        .isAvailable(true)
                        .position(Position.builder().x(5).y(7).build())
                        .creationDate(FRESH).creationAuthor(Author.builder().name(GRABUGE).platform(DISCORD).build())
                        .lastUpdateDate(FRESH).lastAuthorUpdate(Author.builder().name(CHIRON).platform(DOFUS_PORTALS).build())
                        .nearestZaap(Transport.TERRES_DESACREES).transportLimitedNearest(true)
                        .nearestTransportLimited(Transport.ROUTE_DES_ROULOTTES)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ECAFLIPUS).server(AGRIDE).build())
                        .isAvailable(true)
                        .position(Position.builder().x(-666).y(-666).build())
                        .creationDate(FRESH).creationAuthor(Author.builder().name(SONGFU).platform(DISCORD).build())
                        .isUpdated(true).lastUpdateDate(FRESH)
                        .lastAuthorUpdate(Author.builder().name(OSGL).platform(DOFUS_PORTALS).build())
                        .nearestZaap(Transport.CITE_D_ASTRUB)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ENUTROSOR).server(FURYE).build())
                        .isAvailable(true)
                        .position(Position.builder().x(-1).y(1).build())
                        .creationDate(FRESH).creationAuthor(Author.builder().name(KIZARD).platform(DISCORD).build())
                        .isUpdated(true).lastUpdateDate(FRESH)
                        .lastAuthorUpdate(Author.builder().name(CHIRON).platform(DIMTOPIA).build())
                        .nearestZaap(Transport.CITE_D_ASTRUB).transportLimitedNearest(true)
                        .nearestTransportLimited(Transport.BRIGANDIN_ILE_MINOTOROR)
                        .build()
        );
    }
}