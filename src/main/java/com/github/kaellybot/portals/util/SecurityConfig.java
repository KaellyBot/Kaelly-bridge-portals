package com.github.kaellybot.portals.util;

import com.github.kaellybot.portals.model.constants.Privilege;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static com.github.kaellybot.portals.controller.PortalConstants.*;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET   , API + TOKEN_FIND_ALL      ).hasAuthority(Privilege.READ_TOKEN    .name())
                .pathMatchers(HttpMethod.POST  , API + TOKEN_SAVE          ).hasAuthority(Privilege.SAVE_TOKEN    .name())
                .pathMatchers(HttpMethod.DELETE, API + TOKEN_DELETE        ).hasAuthority(Privilege.DELETE_TOKEN  .name())
                .pathMatchers(HttpMethod.GET   , API + SERVER_FIND_ALL     ).hasAuthority(Privilege.READ_SERVER   .name())
                .pathMatchers(HttpMethod.GET   , API + SERVER_FIND_BY_ID   ).hasAuthority(Privilege.READ_SERVER   .name())
                .pathMatchers(HttpMethod.POST  , API + SERVER_SAVE         ).hasAuthority(Privilege.SAVE_SERVER   .name())
                .pathMatchers(HttpMethod.GET   , API + DIMENSION_FIND_ALL  ).hasAuthority(Privilege.READ_DIMENSION.name())
                .pathMatchers(HttpMethod.GET   , API + DIMENSION_FIND_BY_ID).hasAuthority(Privilege.READ_DIMENSION.name())
                .pathMatchers(HttpMethod.POST  , API + DIMENSION_SAVE      ).hasAuthority(Privilege.SAVE_DIMENSION.name())
                .pathMatchers(HttpMethod.GET   , API + PORTAL_FIND_ALL     ).hasAuthority(Privilege.READ_PORTAL   .name())
                .pathMatchers(HttpMethod.GET   , API + PORTAL_FIND_BY_ID   ).hasAuthority(Privilege.READ_PORTAL   .name())
                .pathMatchers(HttpMethod.PATCH , API + PORTAL_MERGE        ).hasAuthority(Privilege.MERGE_PORTAL  .name())
                .anyExchange().authenticated()
                .and().httpBasic()
                .and()
                .build();
    }
}