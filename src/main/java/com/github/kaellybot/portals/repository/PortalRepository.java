package com.github.kaellybot.portals.repository;

import com.github.kaellybot.portals.model.entity.Portal;
import com.github.kaellybot.portals.model.entity.PortalId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface PortalRepository extends ReactiveMongoRepository<Portal, PortalId> {

    Flux<Portal> findAllByPortalIdServer(String server);
}