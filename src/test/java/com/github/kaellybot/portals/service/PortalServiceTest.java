package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.model.constants.Dimension;
import com.github.kaellybot.portals.model.constants.Server;
import com.github.kaellybot.portals.model.entity.Portal;
import com.github.kaellybot.portals.model.entity.PortalId;
import com.github.kaellybot.portals.repository.PortalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PortalServiceTest {

    @Autowired
    private PortalService portalService;

    @Autowired
    private PortalRepository portalRepository;

    @BeforeEach
    void provideData(){
        portalRepository.saveAll(Flux.just(
                Portal.builder()
                        .portalId(PortalId.builder()
                                .server(Server.AGRIDE)
                                .dimension(Dimension.ECAFLIPUS)
                                .build())
                        .build(),
                Portal.builder()
                        .portalId(PortalId.builder()
                                .server(Server.ATCHAM)
                                .dimension(Dimension.ECAFLIPUS)
                                .build())
                        .build()))
                .collectList()
                .block();
    }

    @Test
    void findAllByPortalIdServerTest() {
        StepVerifier.create(portalService.findAllByPortalIdServer(Server.AGRIDE))
                .assertNext(portal -> assertThat(portal.getPortalId()).isNotNull()
                        .extracting(PortalId::getServer).isNotNull().isEqualTo(Server.AGRIDE))
                .expectComplete()
                .verify();
    }

    @Test
    void findAllByPortalIdServerNoPassingCaseTest() {
        StepVerifier.create(portalService.findAllByPortalIdServer(Server.JULITH))
                .expectComplete()
                .verify();
    }

    @Test
    void findByIdTest() {
        StepVerifier.create(portalService.findById(Server.AGRIDE, Dimension.ECAFLIPUS))
                .assertNext(portal -> {
                    assertThat(portal.getPortalId()).isNotNull();
                    assertThat(portal.getPortalId().getServer()).isNotNull().isEqualTo(Server.AGRIDE);
                    assertThat(portal.getPortalId().getDimension()).isNotNull().isEqualTo(Dimension.ECAFLIPUS);
                })
                .expectComplete()
                .verify();
    }

    @Test
    void findByIdNoPassingCaseTest() {
        StepVerifier.create(portalService.findById(Server.BRUMEN, Dimension.ENUTROSOR))
                .expectComplete()
                .verify();
    }

    @Test
    void saveTest() {
        final Portal PORTAL = Portal.builder()
                .portalId(PortalId.builder().server(Server.BRUMEN).dimension(Dimension.SRAMBAD).build())
                .build();
        StepVerifier.create(portalService.save(PORTAL))
                .assertNext(portal -> assertThat(portal).isNotNull().isEqualTo(PORTAL))
                .expectComplete()
                .verify();
    }
}