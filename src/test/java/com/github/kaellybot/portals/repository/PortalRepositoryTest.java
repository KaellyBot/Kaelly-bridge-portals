package com.github.kaellybot.portals.repository;

import com.github.kaellybot.portals.model.constants.Dimension;
import com.github.kaellybot.portals.model.constants.Server;
import com.github.kaellybot.portals.model.entity.Portal;
import com.github.kaellybot.portals.model.entity.PortalId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PortalRepositoryTest {

    @Autowired
    PortalRepository repository;

    @BeforeEach
    void provideData(){
        repository.saveAll(Flux.just(
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
        Flux<Portal> portalFlux = repository.findAllByPortalIdServer(Server.AGRIDE);
        StepVerifier.create(portalFlux)
                .assertNext(portal -> {
                    assertNotNull(portal.getPortalId());
                    assertEquals(Server.AGRIDE, portal.getPortalId().getServer());
                })
                .expectComplete()
                .verify();
    }

    @Test
    void findByIdTest() {
        Mono<Portal> portalMono = repository.findById(PortalId.builder()
                .server(Server.AGRIDE)
                .dimension(Dimension.ECAFLIPUS)
                .build());

        StepVerifier.create(portalMono)
                .assertNext(portal -> {
                    assertNotNull(portal.getPortalId());
                    assertEquals(Server.AGRIDE, portal.getPortalId().getServer());
                    assertEquals(Dimension.ECAFLIPUS, portal.getPortalId().getDimension());
                })
                .expectComplete()
                .verify();
    }
}
