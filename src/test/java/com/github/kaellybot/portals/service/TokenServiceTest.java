package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.mapper.TokenMapper;
import com.github.kaellybot.portals.model.constants.Privilege;
import com.github.kaellybot.portals.model.constants.UserType;
import com.github.kaellybot.portals.model.entity.Token;
import com.github.kaellybot.portals.repository.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TokenServiceTest {

    private static final Token DEFAULT_TOKEN = Token.builder()
            .id("DEFAULT_TOKEN")
            .username("DEFAULT_TOKEN")
            .password("DEFAULT_TOKEN")
            .userType(UserType.USER)
            .privileges(List.of(Privilege.values()))
            .build();

    private static final Token NO_SAVED_TOKEN = Token.builder()
            .id("NO_SAVED_TOKEN")
            .username("NO_SAVED_TOKEN")
            .password("NO_SAVED_TOKEN")
            .userType(UserType.USER)
            .privileges(List.of(Privilege.values()))
            .build();

    @Autowired
    private TokenMapper tokenMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenRepository tokenRepository;

    @BeforeEach
    void provideData(){
        tokenRepository.deleteAll()
                .then(tokenRepository.save(DEFAULT_TOKEN))
                .block();
    }

    @Test
    void findAllTest() {
        StepVerifier.create(tokenService.findAll())
                .assertNext(token -> assertThat(token).isNotNull().isEqualTo(DEFAULT_TOKEN))
                .thenConsumeWhile(x -> true)
                .expectComplete()
                .verify();
    }

    @Test
    void findByUsernameTest() {
        StepVerifier.create(tokenService.findByUsername(DEFAULT_TOKEN.getUsername()))
                .assertNext(token -> assertThat(token).isNotNull().isEqualTo(tokenMapper.mapUserDetails(DEFAULT_TOKEN)))
                .expectComplete()
                .verify();
    }

    @Test
    void findByUsernameNoPassingCaseTest() {
        StepVerifier.create(tokenService.findByUsername(NO_SAVED_TOKEN.getUsername()))
                .expectComplete()
                .verify();
    }

    @Test
    void saveTest() {
        StepVerifier.create(tokenService.save(NO_SAVED_TOKEN))
                .assertNext(portal -> assertThat(portal).isNotNull().isEqualTo(NO_SAVED_TOKEN))
                .expectComplete()
                .verify();
    }

    @Test
    void deleteByIdTest() {
        StepVerifier.create(tokenService.deleteById(DEFAULT_TOKEN.getId()))
                .expectComplete()
                .verify();
        StepVerifier.create(tokenRepository.findById(DEFAULT_TOKEN.getId()))
                .expectComplete()
                .verify();
    }
}