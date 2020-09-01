package com.github.kaellybot.portals.controller;

import com.github.kaellybot.commons.service.DimensionService;
import com.github.kaellybot.commons.service.LanguageService;
import com.github.kaellybot.portals.mapper.DimensionMapper;
import com.github.kaellybot.portals.model.dto.DimensionDto;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.github.kaellybot.portals.controller.PortalConstants.*;
import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;

@RestController
@RequestMapping(API)
@AllArgsConstructor
public class DimensionController {
    private final DimensionService dimensionService;
    private final DimensionMapper dimensionMapper;
    private final LanguageService languageService;

    @GetMapping(path = DIMENSION_FIND_BY_ID, produces=MediaType.APPLICATION_JSON_VALUE)
    public Mono<DimensionDto> findById(@PathVariable(DIMENSION_VAR) String dimensionName,
                                       @RequestHeader(name = ACCEPT_LANGUAGE, required = false) String languageName){
        return dimensionService.findById(dimensionName)
                .map(dimension -> dimensionMapper.map(dimension, languageService
                        .findByAbbreviation(languageName).orElse(DEFAULT_LANGUAGE)))
                .switchIfEmpty(Mono.error(DIMENSION_NOT_FOUND));
    }

    @GetMapping(path = DIMENSION_FIND_ALL, produces=MediaType.APPLICATION_JSON_VALUE)
    public Flux<DimensionDto> findAll(@RequestHeader(name = ACCEPT_LANGUAGE, required = false) String languageName){
        return dimensionService.findAll()
                .map(dimension -> dimensionMapper.map(dimension, languageService
                        .findByAbbreviation(languageName).orElse(DEFAULT_LANGUAGE)));
    }
}