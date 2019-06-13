package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.model.constants.Server;

import java.util.Optional;

public interface IServerService {

    Optional<Server> findByName(String name);
}
