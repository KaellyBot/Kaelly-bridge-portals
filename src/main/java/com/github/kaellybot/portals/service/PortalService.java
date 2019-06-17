package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.model.constants.Dimension;
import com.github.kaellybot.portals.model.constants.Server;
import com.github.kaellybot.portals.model.entity.Portal;
import com.github.kaellybot.portals.model.entity.PortalId;
import com.github.kaellybot.portals.repository.PortalRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PortalService implements IPortalService {

    private PortalRepository portalRepository;

    public PortalService(PortalRepository portalRepository){
        this.portalRepository = portalRepository;
    }

    @Override
    public Mono<Portal> findById(Server server, Dimension dimension){
        return portalRepository.findById(PortalId.builder()
                .server(server)
                .dimension(dimension)
                .build());
    }

    @Override
    public Flux<Portal> findAllByPortalIdServer(Server server) {
        return portalRepository.findAllByPortalIdServer(server);
    }
}