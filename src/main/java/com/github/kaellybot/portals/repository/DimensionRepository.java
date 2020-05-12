package com.github.kaellybot.portals.repository;

import com.github.kaellybot.portals.model.entity.Dimension;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface DimensionRepository extends ReactiveMongoRepository<Dimension, String> {
}