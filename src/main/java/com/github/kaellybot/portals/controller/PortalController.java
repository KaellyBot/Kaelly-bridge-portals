package com.github.kaellybot.portals.controller;

import com.github.kaellybot.portals.mapper.PortalMapper;
import com.github.kaellybot.portals.model.constants.Language;
import com.github.kaellybot.portals.model.dto.ExternalPortalDto;
import com.github.kaellybot.portals.model.dto.PortalDto;
import com.github.kaellybot.portals.model.entity.Dimension;
import com.github.kaellybot.portals.model.entity.Server;
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

import java.util.function.Function;

import static com.github.kaellybot.portals.controller.PortalConstants.*;
import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;

@RestController
@RequestMapping(API)
@AllArgsConstructor
public class PortalController {
    private final PortalService portalService;
    private final ServerService serverService;
    private final DimensionService dimensionService;
    private final LanguageService languageService;
    private final PortalMapper portalMapper;

    @GetMapping(path = FIND_BY_ID, produces=MediaType.APPLICATION_JSON_VALUE)
    public Mono<PortalDto> findById(@PathVariable(SERVER_VAR) String serverName,
                                    @PathVariable(DIMENSION_VAR) String dimensionName,
                                    @RequestHeader(name = ACCEPT_LANGUAGE, required = false) String languageName){
        Language language = languageService.findByName(languageName).orElseThrow(() -> LANGUAGE_NOT_FOUND);
        Mono<Server> server = serverService.findById(serverName).switchIfEmpty(Mono.error(SERVER_NOT_FOUND));
        Mono<Dimension> dimension = dimensionService.findById(dimensionName).switchIfEmpty(Mono.error(DIMENSION_NOT_FOUND));
        return  Mono.zip(server, dimension)
                .flatMap(tuple -> portalService.findById(tuple.getT1(), tuple.getT2())
                        .map(portal -> portalMapper.map(portal, tuple.getT1(), tuple.getT2(), language)));
    }

    @GetMapping(path = FIND_ALL, produces=MediaType.APPLICATION_JSON_VALUE)
    public Flux<PortalDto> findAll(@PathVariable(SERVER_VAR) String serverName,
                                   @RequestHeader(name = ACCEPT_LANGUAGE, required = false) String languageName){

        Language language = languageService.findByName(languageName).orElseThrow(() -> LANGUAGE_NOT_FOUND);

        return serverService.findById(serverName)
                .switchIfEmpty(Mono.error(SERVER_NOT_FOUND))
                .zipWith(dimensionService.findAll().collectMap(Dimension::getId, Function.identity()))
                .flatMapMany(tuple -> portalService.findAllByPortalIdServer(tuple.getT1())
                        .map(portal -> portalMapper.map(portal, tuple.getT1(),
                                tuple.getT2().get(portal.getPortalId().getDimensionId()), language)));
    }

    @PatchMapping(path= MERGE, consumes = MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public Mono<PortalDto> merge(@PathVariable(SERVER_VAR) String serverName,
                                    @PathVariable(DIMENSION_VAR) String dimensionName,
                                    @RequestHeader(name = ACCEPT_LANGUAGE, required = false) String languageName,
                                    @RequestBody @Valid ExternalPortalDto coordinates){

        Language language = languageService.findByName(languageName).orElseThrow(() -> LANGUAGE_NOT_FOUND);
        Mono<Server> server = serverService.findById(serverName).switchIfEmpty(Mono.error(SERVER_NOT_FOUND));
        Mono<Dimension> dimension = dimensionService.findById(dimensionName).switchIfEmpty(Mono.error(DIMENSION_NOT_FOUND));

        return Mono.zip(server, dimension)
                .flatMap(tuple -> portalService.findById(tuple.getT1(), tuple.getT2())
                        .doOnNext(portal -> portal.merge(portalMapper.map(tuple.getT1(), tuple.getT2(), coordinates)))
                        .flatMap(portalService::save)
                        .map(portal -> portalMapper.map(portal, tuple.getT1(), tuple.getT2(), language)));
    }
}