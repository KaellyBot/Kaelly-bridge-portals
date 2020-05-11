package com.github.kaellybot.portals.controller;

import com.github.kaellybot.portals.mapper.PortalMapper;
import com.github.kaellybot.portals.model.constants.Dimension;
import com.github.kaellybot.portals.model.constants.Language;
import com.github.kaellybot.portals.model.dto.ExternalPortalDto;
import com.github.kaellybot.portals.model.dto.PortalDto;
import com.github.kaellybot.portals.service.DimensionService;
import com.github.kaellybot.portals.service.LanguageService;
import com.github.kaellybot.portals.service.PortalService;
import com.github.kaellybot.portals.service.ServerService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static com.github.kaellybot.portals.controller.PortalConstants.*;
import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;

@RestController
@RequestMapping(API)
@AllArgsConstructor
public class PortalController {
    private PortalService portalService;
    private ServerService serverService;
    private DimensionService dimensionService;
    private LanguageService languageService;
    private PortalMapper portalMapper;

    @GetMapping(path = FIND_BY_ID, produces=MediaType.APPLICATION_JSON_VALUE)
    public Mono<PortalDto> findById(@PathVariable(SERVER_VAR) String serverName,
                                    @PathVariable(DIMENSION_VAR) String dimensionName,
                                    @RequestHeader(name = ACCEPT_LANGUAGE, required = false) String languageName){
        Language language = languageService.findByName(languageName).orElseThrow(() -> LANGUAGE_NOT_FOUND);
        Dimension dimension = dimensionService.findByName(dimensionName).orElseThrow(() -> DIMENSION_NOT_FOUND);
        return  serverService.findById(serverName)
                .switchIfEmpty(Mono.error(SERVER_NOT_FOUND))
                .flatMap(server -> portalService.findById(server, dimension))
                .map(portal -> portalMapper.map(portal, language));
    }

    @GetMapping(path = FIND_ALL, produces=MediaType.APPLICATION_JSON_VALUE)
    public Flux<PortalDto> findAll(@PathVariable(SERVER_VAR) String serverName,
                                   @RequestHeader(name = ACCEPT_LANGUAGE, required = false) String languageName){

        Language language = languageService.findByName(languageName).orElseThrow(() -> LANGUAGE_NOT_FOUND);

        return serverService.findById(serverName)
                .switchIfEmpty(Mono.error(SERVER_NOT_FOUND))
                .flatMapMany(server -> portalService.findAllByPortalIdServer(server))
                .map(portal -> portalMapper.map(portal, language));
    }

    @PatchMapping(path= MERGE, consumes = MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public Mono<PortalDto> merge(@PathVariable(SERVER_VAR) String serverName,
                                    @PathVariable(DIMENSION_VAR) String dimensionName,
                                    @RequestHeader(name = ACCEPT_LANGUAGE, required = false) String languageName,
                                    @RequestBody @Valid ExternalPortalDto coordinates){

        Language language = languageService.findByName(languageName).orElseThrow(() -> LANGUAGE_NOT_FOUND);
        Dimension dimension = dimensionService.findByName(dimensionName).orElseThrow(() -> DIMENSION_NOT_FOUND);

        return serverService.findById(serverName)
                .switchIfEmpty(Mono.error(SERVER_NOT_FOUND))
                .flatMap(server -> portalService.findById(server, dimension)
                        .doOnSuccess(portal -> portal.merge(portalMapper.map(server, dimension, coordinates))))
                .flatMap(portalService::save)
                .map(portal -> portalMapper.map(portal, language));
    }
}