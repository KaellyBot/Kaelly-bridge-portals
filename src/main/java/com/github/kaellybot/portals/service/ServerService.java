package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.model.constants.Server;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ServerService implements IServerService {

    @Override
    public Optional<Server> findByName(String name) {
        final String NORMALIZED_NAME = name.toUpperCase().trim();
        return Stream.of(Server.values())
                .filter(server -> server.getName().toUpperCase().trim().equals(NORMALIZED_NAME))
                .findFirst();
    }
}