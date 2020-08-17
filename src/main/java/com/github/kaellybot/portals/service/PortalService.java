package com.github.kaellybot.portals.service;

import com.github.kaellybot.commons.model.entity.Dimension;
import com.github.kaellybot.commons.model.entity.Server;
import com.github.kaellybot.portals.model.entity.Portal;
import com.github.kaellybot.portals.model.entity.PortalId;
import com.github.kaellybot.portals.repository.PortalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class PortalService {

    private final PortalRepository portalRepository;

    public Mono<Portal> findById(Server server, Dimension dimension){
        return portalRepository.findById(PortalId.builder()
                .serverId(server.getId())
                .dimensionId(dimension.getId())
                .build());
    }

    public Flux<Portal> findAllByPortalIdServer(Server server) {
        return portalRepository.findAllByPortalIdServerId(server.getId());
    }

    public Mono<Portal> save(Portal portal) {
        return portalRepository.save(portal);
    }
}