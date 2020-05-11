package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.model.constants.Dimension;
import com.github.kaellybot.portals.model.entity.Portal;
import com.github.kaellybot.portals.model.entity.PortalId;
import com.github.kaellybot.portals.model.entity.Server;
import com.github.kaellybot.portals.repository.PortalRepository;
import com.github.kaellybot.portals.repository.ServerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PortalServiceTest {

    private static final Server DEFAULT_SERVER = Server.builder().id("DEFAULT_SERVER").build();

    private static final Server NO_SAVED_SERVER = Server.builder().id("NO_SAVED_SERVER").build();

    @Autowired
    private PortalService portalService;

    @Autowired
    private PortalRepository portalRepository;

    @Autowired
    private ServerRepository serverRepository;

    @BeforeEach
    void provideData(){
        serverRepository.save(DEFAULT_SERVER)
                .flatMap(server -> portalRepository.save(Portal.builder()
                    .portalId(PortalId.builder().serverId(DEFAULT_SERVER.getId()).dimension(Dimension.ECAFLIPUS).build())
                    .build()))
                .block();
    }

    @Test
    void findAllTest() {
        StepVerifier.create(portalService.findAllByPortalIdServer(DEFAULT_SERVER))
                .assertNext(portal -> assertThat(portal.getPortalId()).isNotNull()
                        .extracting(PortalId::getServerId).isNotNull().isEqualTo(DEFAULT_SERVER.getId()))
                .thenConsumeWhile(x -> true)
                .expectComplete()
                .verify();
    }

    @Test
    void findAllNoPassingCaseTest() {
        StepVerifier.create(portalService.findAllByPortalIdServer(NO_SAVED_SERVER))
                .expectComplete()
                .verify();
    }

    @Test
    void findByIdTest() {
        StepVerifier.create(portalService.findById(DEFAULT_SERVER, Dimension.ECAFLIPUS))
                .assertNext(portal -> {
                    assertThat(portal.getPortalId()).isNotNull();
                    assertThat(portal.getPortalId().getServerId()).isNotNull().isEqualTo(DEFAULT_SERVER.getId());
                    assertThat(portal.getPortalId().getDimension()).isNotNull().isEqualTo(Dimension.ECAFLIPUS);
                })
                .expectComplete()
                .verify();
    }

    @Test
    void findByIdNoPassingCaseTest() {
        StepVerifier.create(portalService.findById(DEFAULT_SERVER, Dimension.ENUTROSOR))
                .expectComplete()
                .verify();
    }

    @Test
    void saveTest() {
        final Portal PORTAL = Portal.builder()
                .portalId(PortalId.builder().serverId(DEFAULT_SERVER.getId()).dimension(Dimension.SRAMBAD).build())
                .build();
        StepVerifier.create(portalService.save(PORTAL))
                .assertNext(portal -> assertThat(portal).isNotNull().isEqualTo(PORTAL))
                .expectComplete()
                .verify();
    }
}