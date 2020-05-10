package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.model.constants.Server;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServerServiceTest {

    @Autowired
    private ServerService serverService;

    @ParameterizedTest
    @EnumSource(Server.class)
    void findPassingCaseTest(Server server){
        assertAll(
                () -> assertThat(serverService.findByName(server.name())).isPresent(),
                () -> assertThat(serverService.findByName(server.name().toLowerCase())).isPresent(),
                () -> assertThat(serverService.findByName(server.name().toUpperCase())).isPresent(),
                () -> assertThat(serverService.findByName(StringUtils.stripAccents(server.name()))).isPresent()
        );

        serverService.findByName(server.name())
                .ifPresent(potentialServer -> assertThat(server).isNotNull().isEqualTo(potentialServer));
    }

    @ParameterizedTest
    @EnumSource(Server.class)
    void findNoPassingCaseTest(Server server){
        assertThat(serverService.findByName(server.name() + "_BAD_NAME")).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("getPassingOtoMustamCase")
    void findPassingOtoMustamCaseTest(String serverName){
        assertThat(serverService.findByName(serverName)).isNotNull().isNotEmpty().contains(Server.OTO_MUSTAM);
    }

    private static Stream<String> getPassingOtoMustamCase(){
        return Stream.of("Oto Mustam", "Oto_Mustam", "Oto-Mustam");
    }
}