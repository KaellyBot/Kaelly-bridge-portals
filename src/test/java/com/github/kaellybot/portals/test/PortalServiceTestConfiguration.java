package com.github.kaellybot.portals.test;

import com.github.kaellybot.commons.service.DimensionService;
import com.github.kaellybot.commons.service.ServerService;
import com.github.kaellybot.portals.service.PortalService;
import com.github.kaellybot.portals.service.TokenService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class PortalServiceTestConfiguration {
    @Bean
    @Primary
    public TokenService tokenService() {
        return Mockito.mock(TokenService.class);
    }

    @Bean
    @Primary
    public PortalService portalService() {
        return Mockito.mock(PortalService.class);
    }

    @Bean
    @Primary
    public ServerService serverService() {
        return Mockito.mock(ServerService.class);
    }

    @Bean
    @Primary
    public DimensionService dimensionService() {
        return Mockito.mock(DimensionService.class);
    }
}