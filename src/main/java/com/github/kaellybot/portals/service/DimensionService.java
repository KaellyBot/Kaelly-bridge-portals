package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.model.entity.Dimension;
import com.github.kaellybot.portals.repository.DimensionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DimensionService {

    private final DimensionRepository dimensionRepository;

    public Mono<Dimension> findById(String id){
        return dimensionRepository.findById(id);
    }

    public Flux<Dimension> findAll(){
        return dimensionRepository.findAll();
    }
}