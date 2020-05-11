package com.github.kaellybot.portals.repository;

import com.github.kaellybot.portals.model.entity.Server;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ServerRepository extends ReactiveMongoRepository<Server, String> {
}