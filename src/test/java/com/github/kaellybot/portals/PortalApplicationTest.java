package com.github.kaellybot.portals;

import org.junit.jupiter.api.Test;

class PortalApplicationTest {

    @Test
    void contextLoads() {
        PortalApplication.main(new String[]{"--spring.main.web-environment=false"});
    }
}