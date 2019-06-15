package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.model.constants.Server;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ServerServiceTest {

    @Autowired
    private IServerService serverService;

    @ParameterizedTest
    @EnumSource(Server.class)
    void findPassingCaseTest(Server server){
        assertTrue(serverService.findByName(server.getName()).isPresent());
        assertTrue(serverService.findByName(server.getName().toLowerCase()).isPresent());
        assertTrue(serverService.findByName(server.getName().toUpperCase()).isPresent());
        assertTrue(serverService.findByName(StringUtils.stripAccents(server.getName())).isPresent());
        assertEquals(server, serverService.findByName(server.getName()).get());
    }

    @ParameterizedTest
    @EnumSource(Server.class)
    void findNoPassingCaseTest(Server server){
        assertFalse(serverService.findByName(server.getName() + "_BAD_NAME").isPresent());
    }
}