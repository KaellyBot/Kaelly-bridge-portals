package com.github.kaellybot.portals.controller;

import com.github.kaellybot.portals.mapper.PortalMapper;
import com.github.kaellybot.portals.model.constants.Dimension;
import com.github.kaellybot.portals.model.constants.Server;
import com.github.kaellybot.portals.model.dto.ExternalPortalDto;
import com.github.kaellybot.portals.model.dto.PortalDto;
import com.github.kaellybot.portals.service.IDimensionService;
import com.github.kaellybot.portals.service.IPortalService;
import com.github.kaellybot.portals.service.IServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.github.kaellybot.portals.controller.PortalConstants.*;

@RestController
@RequestMapping(API)
public class PortalController {

    private static final Logger LOG = LoggerFactory.getLogger(PortalController.class);
    private IPortalService portalService;
    private IServerService serverService;
    private IDimensionService dimensionService;

    public PortalController(IPortalService portalService, IServerService serverService, IDimensionService dimensionService){
        this.portalService = portalService;
        this.serverService = serverService;
        this.dimensionService = dimensionService;
    }

    @GetMapping(path = SERVER_VAR + PORTALS, params = { DIMENSION_VAR, TOKEN_VAR })
    public Mono<PortalDto> findById(@PathVariable(value="server") String serverName,
                                    @RequestParam(value="dimension") String dimensionName,
                                    @RequestParam String token){
        try {
            Server server = serverService.findByName(serverName).orElseThrow(() -> SERVER_NOT_FOUND);
            Dimension dimension = dimensionService.findByName(dimensionName).orElseThrow(() -> DIMENSION_NOT_FOUND);
            return portalService.findById(server, dimension)
                    .map(PortalMapper::map);
        } catch(ResponseStatusException e){
            throw e;
        } catch(Exception e){
            LOG.error("findById", e);
            throw INTERNAL_SERVER_ERROR;
        }
    }

    @GetMapping(path = SERVER_VAR + PORTALS, params = { TOKEN_VAR })
    public Flux<PortalDto> findAllByPortalIdServer(@PathVariable(value="server") String serverName,
                                                   @RequestParam String token){
        try {
            Server server = serverService.findByName(serverName).orElseThrow(() -> SERVER_NOT_FOUND);
            return portalService.findAllByPortalIdServer(server)
                    .map(PortalMapper::map);
        } catch(ResponseStatusException e){
            throw e;
        } catch(Exception e){
            LOG.error("findAllByPortalIdServer", e);
            throw INTERNAL_SERVER_ERROR;
        }
    }

    @PostMapping(path= SERVER_VAR + PORTALS, params = { DIMENSION_VAR, TOKEN_VAR }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> addPortal(@PathVariable(value="server") String serverName,
                                  @RequestParam String dimension,
                                  @RequestParam String token,
                                  @RequestBody ExternalPortalDto coordinates){
        try {
            Server server = serverService.findByName(serverName).orElseThrow(() -> SERVER_NOT_FOUND);
            // TODO
            return Mono.empty();
        } catch(ResponseStatusException e){
            throw e;
        } catch(Exception e){
            LOG.error("addPortal", e);
            throw INTERNAL_SERVER_ERROR;
        }
    }
}