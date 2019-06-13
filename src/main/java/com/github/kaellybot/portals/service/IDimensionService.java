package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.model.constants.Dimension;

import java.util.Optional;

public interface IDimensionService {

    Optional<Dimension> findByName(String name);
}
