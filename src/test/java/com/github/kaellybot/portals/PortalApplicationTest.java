package com.github.kaellybot.portals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PortalApplicationTest {

    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> PortalApplication.main(new String[]{"--spring.main.web-environment=false"}));
    }
}