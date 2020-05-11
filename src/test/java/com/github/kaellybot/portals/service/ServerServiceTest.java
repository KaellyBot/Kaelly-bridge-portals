package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.model.entity.Server;
import com.github.kaellybot.portals.repository.ServerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ServerServiceTest {

    private static final Server DEFAULT_SERVER = Server.builder().id("DEFAULT_SERVER").build();

    @Autowired
    private ServerService serverService;

    @Autowired
    private ServerRepository serverRepository;

    @BeforeEach
    void provideData(){
        serverRepository.save(DEFAULT_SERVER).block();
    }

    @Test
    void findPassingCaseTest(){
        StepVerifier.create(serverService.findById(DEFAULT_SERVER.getId()))
                .assertNext(server -> assertThat(server).isNotNull().isEqualTo(DEFAULT_SERVER))
                .expectComplete()
                .verify();
    }

    @Test
    void findNoPassingCaseTest(){
        StepVerifier.create(serverService.findById(DEFAULT_SERVER.getId() + "_BAD_NAME"))
                .expectComplete()
                .verify();
    }
}