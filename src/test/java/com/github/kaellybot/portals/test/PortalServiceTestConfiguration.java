package com.github.kaellybot.portals.test;

import com.github.kaellybot.portals.service.PortalService;
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
    public PortalService portalService() {
        return Mockito.mock(PortalService.class);
    }
}