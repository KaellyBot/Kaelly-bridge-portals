package com.github.kaellybot.portals.controller;

import com.github.kaellybot.portals.mapper.PortalMapper;
import com.github.kaellybot.portals.model.constants.Dimension;
import com.github.kaellybot.portals.model.constants.Language;
import com.github.kaellybot.portals.model.constants.Server;
import com.github.kaellybot.portals.model.dto.ExternalPortalDto;
import com.github.kaellybot.portals.model.dto.PortalDto;
import com.github.kaellybot.portals.model.entity.Portal;
import com.github.kaellybot.portals.service.IDimensionService;
import com.github.kaellybot.portals.service.ILanguageService;
import com.github.kaellybot.portals.service.IPortalService;
import com.github.kaellybot.portals.service.IServerService;
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
public class PortalController {

    private static final Logger LOG = LoggerFactory.getLogger(PortalController.class);
    private IPortalService portalService;
    private IServerService serverService;
    private IDimensionService dimensionService;
    private ILanguageService languageService;

    public PortalController(IPortalService portalService, IServerService serverService,
                            IDimensionService dimensionService, ILanguageService languageService){
        this.portalService = portalService;
        this.serverService = serverService;
        this.dimensionService = dimensionService;
        this.languageService = languageService;
    }

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
                    .map(portal -> PortalMapper.map(portal, language));
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
                    .map(portal -> PortalMapper.map(portal, language));
        } catch(ResponseStatusException e){
            throw e;
        } catch(Exception e){
            LOG.error("findAllByPortalIdServer", e);
            throw INTERNAL_SERVER_ERROR;
        }
    }

    @PostMapping(path= "{" + SERVER_VAR + "}" + PORTALS, params = { DIMENSION_VAR },
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addPortal(@PathVariable(SERVER_VAR) String serverName,
                                  @RequestParam(DIMENSION_VAR) String dimensionName,
                                  @RequestBody @Valid ExternalPortalDto coordinates){
        try {
            Server server = serverService.findByName(serverName).orElseThrow(() -> SERVER_NOT_FOUND);
            Dimension dimension = dimensionService.findByName(dimensionName).orElseThrow(() -> DIMENSION_NOT_FOUND);
            Mono<Portal> portal = portalService.findById(server, dimension);
            // TODO merge job
        } catch(ResponseStatusException e){
            throw e;
        } catch(Exception e){
            LOG.error("addPortal", e);
            throw INTERNAL_SERVER_ERROR;
        }
    }
}