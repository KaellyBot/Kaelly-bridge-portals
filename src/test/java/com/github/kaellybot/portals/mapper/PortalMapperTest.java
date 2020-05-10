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
import static com.github.kaellybot.portals.model.constants.Server.*;
import static com.github.kaellybot.portals.model.entity.Portal.PORTAL_LIFETIME_IN_DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
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

    @Autowired private PortalMapper portalMapper;
    @Autowired private PositionMapper positionMapper;
    @Autowired private TransportMapper transportMapper;
    @Autowired private AuthorMapper authorMapper;
    @Autowired private Translator translator;

    @ParameterizedTest
    @MethodSource("getPortals")
    void mapPortalDtoTest(Portal portal)
    {
        assertNotNull(portalMapper.map(portal, DEFAULT_LANGUAGE));
        assertEquals(translator.getLabel(DEFAULT_LANGUAGE, portal.getPortalId().getDimension()),
                portalMapper.map(portal, DEFAULT_LANGUAGE).getDimension());
        assertEquals(portal.isAvailable(), portalMapper.map(portal, DEFAULT_LANGUAGE).getIsAvailable());

        if (portal.isValid()){
            assertNotNull(portalMapper.map(portal, DEFAULT_LANGUAGE).getPosition());
            assertEquals(positionMapper.map(portal.getPosition()),
                    portalMapper.map(portal, DEFAULT_LANGUAGE).getPosition());
            assertEquals(portal.getUtilisation(), portalMapper.map(portal, DEFAULT_LANGUAGE).getUtilisation());
            assertEquals(portal.getCreationDate(), portalMapper.map(portal, DEFAULT_LANGUAGE).getCreationDate());
            assertEquals(transportMapper.map(portal.getNearestZaap(), DEFAULT_LANGUAGE),
                    portalMapper.map(portal, DEFAULT_LANGUAGE).getNearestZaap());

            if (portal.isUpdated()){
                assertEquals(portal.getLastUpdateDate(), portalMapper.map(portal, DEFAULT_LANGUAGE).getLastUpdateDate());
                assertEquals(authorMapper.map(portal.getLastAuthorUpdate()),
                        portalMapper.map(portal, DEFAULT_LANGUAGE).getLastAuthorUpdate());
            }
            else {
                assertNull(portalMapper.map(portal, DEFAULT_LANGUAGE).getLastUpdateDate());
                assertNull(portalMapper.map(portal, DEFAULT_LANGUAGE).getLastAuthorUpdate());
            }

            if (portal.isTransportLimitedNearest())
                assertEquals(transportMapper.map(portal.getNearestTransportLimited(), DEFAULT_LANGUAGE),
                        portalMapper.map(portal, DEFAULT_LANGUAGE).getNearestTransportLimited());
            else
                assertNull(portalMapper.map(portal, DEFAULT_LANGUAGE).getNearestTransportLimited());
        }
        else {
            assertNull(portalMapper.map(portal, DEFAULT_LANGUAGE).getPosition());
            assertNull(portalMapper.map(portal, DEFAULT_LANGUAGE).getPosition());
            assertNull(portalMapper.map(portal, DEFAULT_LANGUAGE).getUtilisation());
            assertNull(portalMapper.map(portal, DEFAULT_LANGUAGE).getCreationDate());
            assertNull(portalMapper.map(portal, DEFAULT_LANGUAGE).getLastUpdateDate());
            assertNull(portalMapper.map(portal, DEFAULT_LANGUAGE).getLastAuthorUpdate());
            assertNull(portalMapper.map(portal, DEFAULT_LANGUAGE).getNearestZaap());
            assertNull(portalMapper.map(portal, DEFAULT_LANGUAGE).getNearestTransportLimited());
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
                        .nearestZaap(Transport.ZAAP_PORT_MADRESTAM)
                        .nearestTransportLimited(Transport.FOREUSE_MINE_ISTAIRAMEUR)
                        .build(),

                // Available portals
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ENUTROSOR).server(JULITH).build())
                        .isAvailable(true)
                        .position(Position.builder().x(2).y(0).build())
                        .creationDate(FRESH).creationAuthor(Author.builder().name(BLANCIX).platform(DISCORD).build())
                        .nearestZaap(Transport.ZAAP_BERCEAU)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ENUTROSOR).server(ATCHAM).build())
                        .isAvailable(true)
                        .position(Position.builder().x(5).y(7).build())
                        .creationDate(FRESH).creationAuthor(Author.builder().name(GRABUGE).platform(DISCORD).build())
                        .lastUpdateDate(FRESH).lastAuthorUpdate(Author.builder().name(CHIRON).platform(DOFUS_PORTALS).build())
                        .nearestZaap(Transport.FOREUSE_KARTONPATH).transportLimitedNearest(true)
                        .nearestTransportLimited(Transport.BRIGANDIN_RIVIERE_KAWAII)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ECAFLIPUS).server(AGRIDE).build())
                        .isAvailable(true)
                        .position(Position.builder().x(-666).y(-666).build())
                        .creationDate(FRESH).creationAuthor(Author.builder().name(SONGFU).platform(DISCORD).build())
                        .isUpdated(true).lastUpdateDate(FRESH)
                        .lastAuthorUpdate(Author.builder().name(OSGL).platform(DOFUS_PORTALS).build())
                        .nearestZaap(Transport.ZAAP_CITE_ASTRUB)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ENUTROSOR).server(FURYE).build())
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