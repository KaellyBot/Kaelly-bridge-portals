package com.github.kaellybot.portals.controller;

import com.github.kaellybot.portals.mapper.TokenMapper;
import com.github.kaellybot.portals.model.dto.ExternalTokenDto;
import com.github.kaellybot.portals.model.dto.TokenDto;
import com.github.kaellybot.portals.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static com.github.kaellybot.portals.controller.PortalConstants.*;

@RestController
@RequestMapping(API)
@AllArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    private final TokenMapper tokenMapper;

    @GetMapping(path = PortalConstants.TOKEN_FIND_ALL, produces=MediaType.APPLICATION_JSON_VALUE)
    public Flux<TokenDto> findAll(){
        return tokenService.findAll().map(tokenMapper::map);
    }

    @PostMapping(path = PortalConstants.TOKEN_SAVE, produces=MediaType.APPLICATION_JSON_VALUE)
    public Mono<Object> save(@RequestBody @Valid ExternalTokenDto externalTokenDto){
        return tokenService.findByUsername(externalTokenDto.getUsername())
                .flatMap(e -> Mono.error(TOKEN_ALREADY_EXISTS))
                .switchIfEmpty(tokenService.save(tokenMapper.map(externalTokenDto)).map(tokenMapper::map));
    }

    @DeleteMapping(path = PortalConstants.TOKEN_DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> delete(@PathVariable(TOKEN_VAR) String id){
        return tokenService.deleteById(id);
    }
}