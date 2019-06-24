package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.model.constants.Server;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServerServiceTest {

    @Autowired
    private IServerService serverService;

    @ParameterizedTest
    @EnumSource(Server.class)
    void findPassingCaseTest(Server server){
        assertAll(
                () -> assertTrue(serverService.findByName(server.name()).isPresent()),
                () -> assertTrue(serverService.findByName(server.name().toLowerCase()).isPresent()),
                () -> assertTrue(serverService.findByName(server.name().toUpperCase()).isPresent()),
                () -> assertTrue(serverService.findByName(StringUtils.stripAccents(server.name())).isPresent())
        );

        serverService.findByName(server.name())
                .ifPresent(potentialServer -> assertEquals(server, potentialServer));
    }

    @ParameterizedTest
    @EnumSource(Server.class)
    void findNoPassingCaseTest(Server server){
        assertFalse(serverService.findByName(server.name() + "_BAD_NAME").isPresent());
    }
}