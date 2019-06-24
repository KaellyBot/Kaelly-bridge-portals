package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.constants.Transport;
import com.github.kaellybot.portals.model.entity.Author;
import com.github.kaellybot.portals.model.entity.Portal;
import com.github.kaellybot.portals.model.entity.PortalId;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

import static com.github.kaellybot.portals.mapper.PortalMapper.PORTAL_LIFETIME_IN_DAYS;
import static com.github.kaellybot.portals.model.constants.Dimension.ENUTROSOR;
import static com.github.kaellybot.portals.model.constants.Server.FURYE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PortalMapperTest {

    private final static Instant OLD = Instant.parse("2019-01-10T00:00:00.00Z");
    private final static Instant FRESH = Instant.parse("2019-02-10T00:00:00.00Z");
    private final static Instant NOW = Instant.parse("2019-03-10T00:00:00.00Z");

    @ParameterizedTest
    @MethodSource("getPortals")
    void mapPortalDtoTest(Portal portal)
    {
        assertNotNull(PortalMapper.map(portal));
        assertEquals(portal.getPortalId().getDimension().name(), PortalMapper.map(portal).getDimension());
        assertEquals(portal.isAvailable(), PortalMapper.map(portal).getIsAvailable());

        if (isPortalStillFresh(portal)){
            assertNotNull(PortalMapper.map(portal).getPosition());
            assertEquals(PositionMapper.map(portal.getPosition()), PortalMapper.map(portal).getPosition());
            assertEquals(portal.getUtilisation(), PortalMapper.map(portal).getUtilisation());
            assertEquals(portal.getCreationDate(), PortalMapper.map(portal).getCreationDate());
            assertEquals(TransportMapper.map(portal.getNearestZaap()), PortalMapper.map(portal).getNearestZaap());

            if (portal.isUpdated()){
                assertEquals(portal.getLastUpdateDate(), PortalMapper.map(portal).getLastUpdateDate());
                assertEquals(AuthorMapper.map(portal.getLastAuthorUpdate()), PortalMapper.map(portal).getLastAuthorUpdate());
            }
            else {
                assertNull(PortalMapper.map(portal).getLastUpdateDate());
                assertNull(PortalMapper.map(portal).getLastAuthorUpdate());
            }

            if (portal.isTransportLimitedNearest())
                assertEquals(TransportMapper.map(portal.getNearestTransportLimited()),
                        PortalMapper.map(portal).getNearestTransportLimited());
            else
                assertNull(PortalMapper.map(portal).getNearestTransportLimited());
        }
        else {
            assertNull(PortalMapper.map(portal).getPosition());
            assertNull(PortalMapper.map(portal).getPosition());
            assertNull(PortalMapper.map(portal).getUtilisation());
            assertNull(PortalMapper.map(portal).getCreationDate());
            assertNull(PortalMapper.map(portal).getLastUpdateDate());
            assertNull(PortalMapper.map(portal).getLastAuthorUpdate());
            assertNull(PortalMapper.map(portal).getNearestZaap());
            assertNull(PortalMapper.map(portal).getNearestTransportLimited());
        }
    }

    private static Stream<Portal> getPortals() {
        return Stream.of(
                // No available portals
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ENUTROSOR).server(FURYE).build())
                        .isAvailable(true)
                        .creationDate(OLD)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ENUTROSOR).server(FURYE).build())
                        .isAvailable(false)
                        .build(),

                // Available portals
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ENUTROSOR).server(FURYE).build())
                        .isAvailable(true)
                        .creationDate(FRESH).creationAuthor(Author.builder().name("Kaysoro").platform("Discord").build())
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ENUTROSOR).server(FURYE).build())
                        .isAvailable(true)
                        .creationDate(FRESH).creationAuthor(Author.builder().name("Songfu").platform("Discord").build())
                        .lastUpdateDate(FRESH).lastAuthorUpdate(Author.builder().name("Chiron").platform("Dofus-portals").build())
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ENUTROSOR).server(FURYE).build())
                        .isAvailable(true)
                        .creationDate(FRESH).creationAuthor(Author.builder().name("Songfu").platform("Discord").build())
                        .nearestZaap(Transport.CITE_D_ASTRUB)
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder().dimension(ENUTROSOR).server(FURYE).build())
                        .isAvailable(true)
                        .creationDate(FRESH).creationAuthor(Author.builder().name("Songfu").platform("Discord").build())
                        .nearestZaap(Transport.CITE_D_ASTRUB)
                        .nearestTransportLimited(Transport.BRIGANDIN_ILE_MINOTOROR)
                        .build()
        );
    }

    private static boolean isPortalStillFresh(Portal portal){
        return portal.isAvailable() &&
                Math.abs(Duration.between(NOW, portal.getCreationDate()).toDays()) < PORTAL_LIFETIME_IN_DAYS;
    }
}