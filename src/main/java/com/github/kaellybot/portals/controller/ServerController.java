package com.github.kaellybot.portals.controller;

import com.github.kaellybot.commons.service.LanguageService;
import com.github.kaellybot.commons.service.ServerService;
import com.github.kaellybot.portals.mapper.ServerMapper;
import com.github.kaellybot.portals.model.dto.ExternalServerDto;
import com.github.kaellybot.portals.model.dto.ServerDto;
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
public class ServerController {
    private final ServerService serverService;
    private final ServerMapper serverMapper;
    private final LanguageService languageService;

    @GetMapping(path = SERVER_FIND_BY_ID, produces=MediaType.APPLICATION_JSON_VALUE)
    public Mono<ServerDto> findById(@PathVariable(SERVER_VAR) String serverName,
                                    @RequestHeader(name = ACCEPT_LANGUAGE, required = false) String languageName){
        return serverService.findById(serverName)
                .map(server -> serverMapper.map(server, languageService
                        .findByAbbreviation(languageName).orElse(DEFAULT_LANGUAGE)))
                .switchIfEmpty(Mono.error(SERVER_NOT_FOUND));
    }

    @GetMapping(path = SERVER_FIND_ALL, produces=MediaType.APPLICATION_JSON_VALUE)
    public Flux<ServerDto> findAll(@RequestHeader(name = ACCEPT_LANGUAGE, required = false) String languageName){
        return serverService.findAll()
                .map(server -> serverMapper.map(server, languageService
                        .findByAbbreviation(languageName).orElse(DEFAULT_LANGUAGE)));
    }

    @PostMapping(path = SERVER_SAVE, consumes = MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public Mono<ServerDto> save(@RequestHeader(name = ACCEPT_LANGUAGE, required = false) String languageName,
                                @RequestBody @Valid ExternalServerDto serverDto){
        return serverService.save(serverMapper.map(serverDto))
                .map(server -> serverMapper.map(server, languageService
                        .findByAbbreviation(languageName).orElse(DEFAULT_LANGUAGE)));
    }
}