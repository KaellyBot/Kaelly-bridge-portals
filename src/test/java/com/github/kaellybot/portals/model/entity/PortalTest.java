package com.github.kaellybot.portals.model.entity;

import com.github.kaellybot.portals.model.constants.Transport;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Stream;

import static com.github.kaellybot.portals.model.entity.Portal.PORTAL_LIFETIME_IN_DAYS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PortalTest {
    private static final String DEFAULT_SERVER = "DEFAULT_SERVER";
    private static final String DEFAULT_DIMENSION = "DEFAULT_DIMENSION";

    private static final Instant FRESH = Instant.now();
    private static final Instant STILl_FRESH = FRESH.minus(PORTAL_LIFETIME_IN_DAYS - 1, ChronoUnit.DAYS);
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
                () -> assertThat(portal.getPosition()).isNotNull().isNotEqualTo(OLD_POSITION),
                () -> assertThat(portal.getCreationDate()).isNotNull().isNotEqualTo(OLD_CREATION_DATE)
        );

        assertAll(
                () -> assertThat(portal.getPosition()).isNotNull().isEqualTo(NEW_PORTAL.getPosition()),
                () -> assertThat(portal.getUtilisation()).isNotNull().isEqualTo(NEW_PORTAL.getUtilisation()),
                () -> assertThat(portal.getCreationDate()).isNotNull().isEqualTo(NEW_PORTAL.getCreationDate()),
                () -> assertThat(portal.getCreationAuthor()).isNotNull().isEqualTo(NEW_PORTAL.getCreationAuthor()),
                () -> assertThat(portal.getIsAvailable()).isNotNull().isTrue()
        );

        assertAll(
                () -> assertThat(portal.getIsUpdated()).isNotNull().isFalse(),
                () -> assertThat(portal.getLastAuthorUpdate()).isNull(),
                () -> assertThat(portal.getLastUpdateDate()).isNull()
        );

        assertAll(
                () -> assertThat(portal.getNearestZaap()).isNotNull().isEqualTo(Transport.ZAAP_VILLAGE_AMAKNA),
                () -> assertThat(portal.getTransportLimitedNearest()).isNotNull().isFalse(),
                () -> assertThat(portal.getNearestTransportLimited()).isNull()
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
                () -> assertThat(portal.getPosition()).isNotNull().isEqualTo(OLD_POSITION),
                () -> assertThat(portal.getCreationAuthor()).isNotNull().isEqualTo(OLD_CREATION_AUTHOR),
                () -> assertThat(portal.getCreationDate()).isNotNull().isEqualTo(OLD_CREATION_DATE),
                () -> assertThat(portal.getIsAvailable()).isNotNull().isTrue()
        );

        assertAll(
                () -> assertThat(portal.getIsUpdated()).isNotNull().isTrue(),
                () -> assertThat(portal.getLastAuthorUpdate()).isEqualTo(NEW_PORTAL.getLastAuthorUpdate()),
                () -> assertThat(portal.getLastUpdateDate()).isEqualTo(NEW_PORTAL.getLastUpdateDate()),
                () -> assertThat(portal.getUtilisation()).isNotNull().isEqualTo(NEW_PORTAL.getUtilisation())
        );
    }

    @ParameterizedTest
    @MethodSource("getAvailablePortals")
    void determineTransportTest(Portal portal){
        final Transport EXPECTED_ZAAP = portal.getNearestZaap();
        final Boolean EXPECTED_IS_LIMITED_NEAREST = Optional.ofNullable(portal.getTransportLimitedNearest()).orElse(false);
        final Transport EXPECTED_LIMITED_TRANSPORT = portal.getNearestTransportLimited();

        portal.determineTransports();

        assertAll(
                () -> assertThat(portal.getNearestZaap()).isNotNull().isEqualTo(EXPECTED_ZAAP),
                () -> assertThat(portal.getTransportLimitedNearest()).isNotNull().isEqualTo(EXPECTED_IS_LIMITED_NEAREST),
                () -> assertThat(portal.getNearestTransportLimited()).isEqualTo(EXPECTED_LIMITED_TRANSPORT)
        );
    }

    private static Stream<Portal> getAvailablePortals(){
        return getPortals()
                .filter(portal -> Optional.ofNullable(portal.getIsAvailable()).orElse(false))
                .filter(portal -> portal.getPosition() != null);
    }

    private static Stream<Portal> getPortals() {
        return Stream.of(
                // No available portals
                Portal.builder()
                        .portalId(PortalId.builder().dimensionId(DEFAULT_DIMENSION + "_1").serverId(DEFAULT_SERVER).build())
                        .isAvailable(true)
                        .creationDate(OLD)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimensionId(DEFAULT_DIMENSION + "_2").serverId(DEFAULT_SERVER).build())
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimensionId(DEFAULT_DIMENSION + "_3").serverId(DEFAULT_SERVER).build())
                        .isAvailable(true)
                        .creationDate(OLD).creationAuthor(Author.builder().name(OSGL).platform(DISCORD).build())
                        .nearestZaap(Transport.ZAAP_PORT_MADRESTAM)
                        .nearestTransportLimited(Transport.FOREUSE_MINE_ISTAIRAMEUR)
                        .build(),

                // Available portals
                Portal.builder()
                        .portalId(PortalId.builder().dimensionId(DEFAULT_DIMENSION + "_4").serverId(DEFAULT_SERVER).build())
                        .isAvailable(true)
                        .position(Transport.ZAAP_BERCEAU.getPosition())
                        .creationDate(STILl_FRESH).creationAuthor(Author.builder().name(BLANCIX).platform(DISCORD).build())
                        .nearestZaap(Transport.ZAAP_BERCEAU)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimensionId(DEFAULT_DIMENSION + "_5").serverId(DEFAULT_SERVER).build())
                        .isAvailable(true)
                        .position(Transport.CHAR_TERRITOIRE_CACTERRE.getPosition())
                        .creationDate(STILl_FRESH).creationAuthor(Author.builder().name(GRABUGE).platform(DISCORD).build())
                        .lastUpdateDate(STILl_FRESH).lastAuthorUpdate(Author.builder().name(CHIRON).platform(DOFUS_PORTALS).build())
                        .nearestZaap(Transport.ZAAP_DUNE_OSSEMENTS).transportLimitedNearest(true)
                        .nearestTransportLimited(Transport.CHAR_TERRITOIRE_CACTERRE)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimensionId(DEFAULT_DIMENSION + "_6").serverId(DEFAULT_SERVER).build())
                        .isAvailable(true)
                        .position(Transport.ZAAP_CITE_ASTRUB.getPosition())
                        .creationDate(STILl_FRESH).creationAuthor(Author.builder().name(SONGFU).platform(DISCORD).build())
                        .isUpdated(true).lastUpdateDate(STILl_FRESH)
                        .lastAuthorUpdate(Author.builder().name(OSGL).platform(DOFUS_PORTALS).build())
                        .nearestZaap(Transport.ZAAP_CITE_ASTRUB)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimensionId(DEFAULT_DIMENSION + "_7").serverId(DEFAULT_SERVER).build())
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