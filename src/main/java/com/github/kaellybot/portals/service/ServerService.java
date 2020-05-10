package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.model.constants.Server;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ServerService {

    public Optional<Server> findByName(String name) {
        final String NORMALIZED_NAME = normalizeServerName(name);
        return Stream.of(Server.values())
                .filter(server -> normalizeServerName(server.name()).equals(NORMALIZED_NAME))
                .findFirst();
    }

    private String normalizeServerName(String input){
        return StringUtils.stripAccents(input.toUpperCase().replaceAll("\\s|-", "_").trim());
    }
}