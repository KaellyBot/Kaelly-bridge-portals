package com.github.kaellybot.portals.controller;

import com.github.kaellybot.portals.mapper.PortalMapper;
import com.github.kaellybot.portals.model.constants.Dimension;
import com.github.kaellybot.portals.model.constants.Language;
import com.github.kaellybot.portals.model.constants.Server;
import com.github.kaellybot.portals.model.dto.ExternalPortalDto;
import com.github.kaellybot.portals.model.dto.PortalDto;
import com.github.kaellybot.portals.model.entity.Portal;
import com.github.kaellybot.portals.service.DimensionService;
import com.github.kaellybot.portals.service.LanguageService;
import com.github.kaellybot.portals.service.PortalService;
import com.github.kaellybot.portals.service.ServerService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static com.github.kaellybot.portals.controller.PortalConstants.*;
import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;

@RestController
@RequestMapping(API)
@AllArgsConstructor
public class PortalController {

    private static final Logger LOG = LoggerFactory.getLogger(PortalController.class);
    private PortalService portalService;
    private ServerService serverService;
    private DimensionService dimensionService;
    private LanguageService languageService;
    private PortalMapper portalMapper;

    @GetMapping(path = "{" + SERVER_VAR + "}" + PORTALS, params = { DIMENSION_VAR },
            produces=MediaType.APPLICATION_JSON_VALUE)
    public Mono<PortalDto> findById(@PathVariable(SERVER_VAR) String serverName,
                                    @RequestParam(DIMENSION_VAR) String dimensionName,
                                    @RequestHeader(name = ACCEPT_LANGUAGE, required = false) String languageName){
        try {
            Language language = languageService.findByName(languageName).orElseThrow(() -> LANGUAGE_NOT_FOUND);
            Server server = serverService.findByName(serverName).orElseThrow(() -> SERVER_NOT_FOUND);
            Dimension dimension = dimensionService.findByName(dimensionName).orElseThrow(() -> DIMENSION_NOT_FOUND);
            return portalService.findById(server, dimension)
                    .map(portal -> portalMapper.map(portal, language));
        } catch(ResponseStatusException e){
            throw e;
        } catch(Exception e){
            LOG.error("findById", e);
            throw INTERNAL_SERVER_ERROR;
        }
    }

    @GetMapping(path = "{" + SERVER_VAR + "}" + PORTALS, produces=MediaType.APPLICATION_JSON_VALUE)
    public Flux<PortalDto> findAllByPortalIdServer(@PathVariable(SERVER_VAR) String serverName,
                                                   @RequestHeader(name = ACCEPT_LANGUAGE, required = false)
                                                               String languageName){
        try {
            Language language = languageService.findByName(languageName).orElseThrow(() -> LANGUAGE_NOT_FOUND);
            Server server = serverService.findByName(serverName).orElseThrow(() -> SERVER_NOT_FOUND);
            return portalService.findAllByPortalIdServer(server)
                    .map(portal -> portalMapper.map(portal, language));
        } catch(ResponseStatusException e){
            throw e;
        } catch(Exception e){
            LOG.error("findAllByPortalIdServer", e);
            throw INTERNAL_SERVER_ERROR;
        }
    }

    @PostMapping(path= "{" + SERVER_VAR + "}" + PORTALS, params = { DIMENSION_VAR },
            consumes = MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public Mono<PortalDto> addPortal(@PathVariable(SERVER_VAR) String serverName,
                                    @RequestParam(DIMENSION_VAR) String dimensionName,
                                    @RequestHeader(name = ACCEPT_LANGUAGE, required = false) String languageName,
                                    @RequestBody @Valid ExternalPortalDto coordinates){
        try {
            Language language = languageService.findByName(languageName).orElseThrow(() -> LANGUAGE_NOT_FOUND);
            Server server = serverService.findByName(serverName).orElseThrow(() -> SERVER_NOT_FOUND);
            Dimension dimension = dimensionService.findByName(dimensionName).orElseThrow(() -> DIMENSION_NOT_FOUND);
            Portal externalPortal = portalMapper.map(server, dimension, coordinates);

            return portalService.findById(server, dimension)
                    .doOnSuccess(portal -> portal.merge(externalPortal))
                    .flatMap(portalService::save)
                    .map(portal -> portalMapper.map(portal, language));
        } catch(ResponseStatusException e){
            throw e;
        } catch(Exception e){
            LOG.error("addPortal", e);
            throw INTERNAL_SERVER_ERROR;
        }
    }
}