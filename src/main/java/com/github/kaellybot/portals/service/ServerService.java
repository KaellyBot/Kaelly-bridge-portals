package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.model.entity.Server;
import com.github.kaellybot.portals.repository.ServerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@AllArgsConstructor
public class ServerService {

    private final ServerRepository serverRepository;

    public Mono<Server> findById(String id) {
        return serverRepository.findById(id);
    }
}