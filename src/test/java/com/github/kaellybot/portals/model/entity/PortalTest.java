package com.github.kaellybot.portals.model.entity;

import com.github.kaellybot.portals.model.constants.Transport;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import static com.github.kaellybot.portals.model.constants.Dimension.*;
import static com.github.kaellybot.portals.model.constants.Dimension.ENUTROSOR;
import static com.github.kaellybot.portals.model.constants.Server.*;
import static com.github.kaellybot.portals.model.constants.Server.AGRIDE;
import static com.github.kaellybot.portals.model.constants.Server.FURYE;
import static com.github.kaellybot.portals.model.entity.Portal.PORTAL_LIFETIME_IN_DAYS;
import static org.junit.jupiter.api.Assertions.*;

class PortalTest {
    private final static Instant FRESH = Instant.now();
    private final static Instant STILl_FRESH = FRESH.minus(PORTAL_LIFETIME_IN_DAYS - 1, ChronoUnit.DAYS);
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
    void mergeNewPositionTest(Portal portal){
        final Position OLD_POSITION = portal.getPosition();
        final Instant OLD_CREATION_DATE = portal.getCreationDate();
        final Portal NEW_PORTAL = Portal.builder()
                .portalId(portal.getPortalId())
                .position(Transport.ZAAP_VILLAGE_AMAKNA.getPosition())
                .utilisation(1)
                .creationAuthor(Author.builder().name(BLANCIX).platform(DIMTOPIA).build())
                .creationDate(FRESH)
                .build();

        portal.merge(NEW_PORTAL);

        assertAll(
                () -> assertNotEquals(OLD_POSITION, portal.getPosition()),
                () -> assertNotEquals(OLD_CREATION_DATE, portal.getCreationDate())
        );

        assertAll(
                () -> assertEquals(NEW_PORTAL.getPosition(), portal.getPosition()),
                () -> assertEquals(NEW_PORTAL.getUtilisation(), portal.getUtilisation()),
                () -> assertEquals(NEW_PORTAL.getCreationDate(), portal.getCreationDate()),
                () -> assertEquals(NEW_PORTAL.getCreationAuthor(), portal.getCreationAuthor()),
                () -> assertTrue(portal.isAvailable())
        );

        assertAll(
                () -> assertFalse(portal.isUpdated()),
                () -> assertNull(portal.getLastAuthorUpdate()),
                () -> assertNull(portal.getLastUpdateDate())
        );

        assertAll(
                () -> assertEquals(Transport.ZAAP_VILLAGE_AMAKNA, portal.getNearestZaap()),
                () -> assertFalse(portal.isTransportLimitedNearest()),
                () -> assertNull(portal.getNearestTransportLimited())
        );
    }

    @ParameterizedTest
    @MethodSource("getAvailablePortals")
    void mergeSamePositionTest(Portal portal){
        final Position OLD_POSITION = portal.getPosition();
        final Author OLD_CREATION_AUTHOR = portal.getCreationAuthor();
        final Instant OLD_CREATION_DATE = portal.getCreationDate();
        final Portal NEW_PORTAL = Portal.builder()
                .portalId(portal.getPortalId())
                .position(portal.getPosition())
                .utilisation(1)
                .creationAuthor(Author.builder().name(BLANCIX).platform(DIMTOPIA).build())
                .creationDate(FRESH)
                .build();
        portal.merge(NEW_PORTAL);

        assertAll(
                () -> assertEquals(OLD_POSITION, portal.getPosition()),
                () -> assertEquals(OLD_CREATION_AUTHOR, portal.getCreationAuthor()),
                () -> assertEquals(OLD_CREATION_DATE, portal.getCreationDate()),
                () -> assertTrue(portal.isAvailable())
        );

        assertAll(
                () -> assertTrue(portal.isUpdated()),
                () -> assertEquals(NEW_PORTAL.getLastAuthorUpdate(), portal.getLastAuthorUpdate()),
                () -> assertEquals(NEW_PORTAL.getLastUpdateDate(), portal.getLastUpdateDate()),
                () -> assertEquals(NEW_PORTAL.getUtilisation(), portal.getUtilisation())
        );
    }

    @ParameterizedTest
    @MethodSource("getAvailablePortals")
    void determineTransportTest(Portal portal){
        final Transport EXPECTED_ZAAP = portal.getNearestZaap();
        final boolean EXPECTED_IS_LIMITED_NEAREST = portal.isTransportLimitedNearest();
        final Transport EXPECTED_LIMITED_TRANSPORT = portal.getNearestTransportLimited();

        portal.determineTransports();

        assertAll(
                () -> assertEquals(EXPECTED_ZAAP, portal.getNearestZaap()),
                () -> assertEquals(EXPECTED_IS_LIMITED_NEAREST, portal.isTransportLimitedNearest()),
                () -> assertEquals(EXPECTED_LIMITED_TRANSPORT, portal.getNearestTransportLimited())
        );
    }

    private static Stream<Portal> getAvailablePortals(){
        return getPortals()
                .filter(Portal::isAvailable)
                .filter(portal -> portal.getPosition() != null);
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
                        .position(Transport.ZAAP_BERCEAU.getPosition())
                        .creationDate(STILl_FRESH).creationAuthor(Author.builder().name(BLANCIX).platform(DISCORD).build())
                        .nearestZaap(Transport.ZAAP_BERCEAU)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ENUTROSOR).server(ATCHAM).build())
                        .isAvailable(true)
                        .position(Transport.CHAR_TERRITOIRE_CACTERRE.getPosition())
                        .creationDate(STILl_FRESH).creationAuthor(Author.builder().name(GRABUGE).platform(DISCORD).build())
                        .lastUpdateDate(STILl_FRESH).lastAuthorUpdate(Author.builder().name(CHIRON).platform(DOFUS_PORTALS).build())
                        .nearestZaap(Transport.ZAAP_DUNE_OSSEMENTS).transportLimitedNearest(true)
                        .nearestTransportLimited(Transport.CHAR_TERRITOIRE_CACTERRE)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ECAFLIPUS).server(AGRIDE).build())
                        .isAvailable(true)
                        .position(Transport.ZAAP_CITE_ASTRUB.getPosition())
                        .creationDate(STILl_FRESH).creationAuthor(Author.builder().name(SONGFU).platform(DISCORD).build())
                        .isUpdated(true).lastUpdateDate(STILl_FRESH)
                        .lastAuthorUpdate(Author.builder().name(OSGL).platform(DOFUS_PORTALS).build())
                        .nearestZaap(Transport.ZAAP_CITE_ASTRUB)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ENUTROSOR).server(FURYE).build())
                        .isAvailable(true)
                        .position(Transport.BRIGANDIN_ILE_MINOTOROR.getPosition())
                        .creationDate(STILl_FRESH).creationAuthor(Author.builder().name(KIZARD).platform(DISCORD).build())
                        .isUpdated(true).lastUpdateDate(STILl_FRESH)
                        .lastAuthorUpdate(Author.builder().name(CHIRON).platform(DIMTOPIA).build())
                        .nearestZaap(Transport.ZAAP_ROUTES_ROCAILLEUSES).transportLimitedNearest(true)
                        .nearestTransportLimited(Transport.BRIGANDIN_ILE_MINOTOROR)
                        .build()
        );
    }
}