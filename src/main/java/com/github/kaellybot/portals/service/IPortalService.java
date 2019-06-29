package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.model.constants.Dimension;
import com.github.kaellybot.portals.model.constants.Server;
import com.github.kaellybot.portals.model.entity.Portal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPortalService {

    Mono<Portal> findById(Server server, Dimension dimension);

    Flux<Portal> findAllByPortalIdServer(Server server);

    Mono<Portal> save(Portal portal);
}
