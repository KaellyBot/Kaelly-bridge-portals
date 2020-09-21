package com.github.kaellybot.portals.controller;

import com.github.kaellybot.commons.model.constants.Language;
import com.github.kaellybot.commons.model.entity.Server;
import com.github.kaellybot.commons.repository.ServerRepository;
import com.github.kaellybot.portals.mapper.ServerMapper;
import com.github.kaellybot.portals.model.dto.ExternalServerDto;
import com.github.kaellybot.portals.model.dto.ServerDto;
import com.github.kaellybot.portals.test.Privilege;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.kaellybot.portals.controller.PortalConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureWebTestClient
class ServerControllerTest {

    private static final Server DEFAULT_SERVER = Server.builder().id("DEFAULT_SERVER").build();
    private static final String URL = "http://image.png";
    private static final String GOULTARD = "Goultard";
    private static final String DJAUL = "Djaul";
    private static final String JIVA = "JIVA";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private ServerMapper serverMapper;

    @BeforeEach
    void provideData(){
        serverRepository.saveAll(getServers().collect(Collectors.toList()))
                .then(serverRepository.save(DEFAULT_SERVER)).block();
    }

    @ParameterizedTest
    @WithMockUser(authorities = {Privilege.READ_SERVER})
    @MethodSource("getServers")
    void findByIdTest(Server server){
        webTestClient.get()
                .uri(API + SERVER_FIND_BY_ID.replace("{" + SERVER_VAR + "}", server.getId()))
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(ServerDto.class)
                .consumeWith(t -> assertEquals(serverMapper.map(server, DEFAULT_LANGUAGE), t.getResponseBody()));
    }

    @Test
    @WithMockUser(authorities = {Privilege.READ_SERVER})
    void findByIdExceptionTest(){
        webTestClient.get()
                .uri(API + SERVER_FIND_BY_ID.replace("{" + SERVER_VAR + "}", "NO_SERVER"))
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull().contains(SERVER_NOT_FOUND_MESSAGE));

        webTestClient.get()
                .uri(API + SERVER_FIND_BY_ID.replace("{" + SERVER_VAR + "}",  DEFAULT_SERVER.getId()))
                .header(ACCEPT_LANGUAGE, "NO_LANGUAGE")
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(ServerDto.class);
    }

    @Test
    void findByIdAuthenticationTest(){
        webTestClient.get()
                .uri(API + SERVER_FIND_BY_ID.replace("{" + SERVER_VAR + "}",  DEFAULT_SERVER.getId()))
                .exchange()
                .expectStatus().isEqualTo(UNAUTHORIZED);
    }

    @Test
    @WithMockUser
    void findByIdAuthorizationTest(){
        webTestClient.get()
                .uri(API + SERVER_FIND_BY_ID.replace("{" + SERVER_VAR + "}",  DEFAULT_SERVER.getId()))
                .exchange()
                .expectStatus().isEqualTo(FORBIDDEN);
    }

    @ParameterizedTest
    @WithMockUser(authorities = {Privilege.READ_SERVER})
    @MethodSource("getServers")
    void findAllTest(Server server){
        webTestClient.get()
                .uri(API + SERVER_FIND_ALL)
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBodyList(ServerDto.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(serverMapper.map(server, DEFAULT_LANGUAGE)));
    }

    @Test
    @WithMockUser(authorities = {Privilege.READ_SERVER})
    void findAllExceptionTest(){
        webTestClient.get()
                .uri(API + SERVER_FIND_ALL)
                .header(ACCEPT_LANGUAGE, "NO_LANGUAGE")
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBodyList(ServerDto.class);
    }

    @Test
    void findAllAuthenticationTest(){
        webTestClient.get()
                .uri(API + SERVER_FIND_ALL)
                .exchange()
                .expectStatus().isEqualTo(UNAUTHORIZED);
    }

    @Test
    @WithMockUser
    void findAllAuthorizationTest(){
        webTestClient.get()
                .uri(API + SERVER_FIND_ALL)
                .exchange()
                .expectStatus().isEqualTo(FORBIDDEN);
    }

    @ParameterizedTest
    @WithMockUser(authorities = {Privilege.SAVE_SERVER})
    @MethodSource("getExternalServers")
    void saveTest(ExternalServerDto server){
        webTestClient.post()
                .uri(API + SERVER_SAVE)
                .bodyValue(server)
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(ServerDto.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .satisfies(serverDto -> {
                            assertThat(serverDto.getId()).isEqualTo(server.getId());
                            assertThat(serverDto.getImage()).isEqualTo(server.getImage());
                            assertThat(serverDto.getName()).isEqualTo(server.getLabels().get(DEFAULT_LANGUAGE));
                        }));
    }

    @Test
    @WithMockUser(authorities = {Privilege.SAVE_SERVER})
    void saveMissingIdTest(){
        final ExternalServerDto SERVER = ExternalServerDto.builder()
                .image(URL)
                .labels(Map.of(Language.FR, GOULTARD, Language.EN, GOULTARD, Language.ES, GOULTARD))
                .build();

        webTestClient.post()
                .uri(API + SERVER_SAVE)
                .bodyValue(SERVER)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(ID_NOT_FOUND_MESSAGE));
    }

    @Test
    @WithMockUser(authorities = {Privilege.SAVE_SERVER})
    void saveMissingImageTest(){
        final ExternalServerDto SERVER = ExternalServerDto.builder()
                .id(GOULTARD)
                .labels(Map.of(Language.FR, GOULTARD, Language.EN, GOULTARD, Language.ES, GOULTARD))
                .build();

        webTestClient.post()
                .uri(API + SERVER_SAVE)
                .bodyValue(SERVER)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(IMAGE_NOT_FOUND_MESSAGE));
    }

    @ParameterizedTest
    @WithMockUser(authorities = {Privilege.SAVE_SERVER})
    @MethodSource("getExternalServersWithMissingLabel")
    void saveMissingLabelTest(ExternalServerDto server ){
        webTestClient.post()
                .uri(API + SERVER_SAVE)
                .bodyValue(server)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(String.class)
                .consumeWith(t -> assertThat(t.getResponseBody()).isNotNull()
                        .contains(LABEL_NOT_FOUND_MESSAGE));
    }

    @Test
    void saveAuthenticationTest(){
        webTestClient.post()
                .uri(API + SERVER_SAVE)
                .exchange()
                .expectStatus().isEqualTo(UNAUTHORIZED);
    }

    @Test
    @WithMockUser
    void saveAuthorizationTest(){
        webTestClient.post()
                .uri(API + SERVER_SAVE)
                .exchange()
                .expectStatus().isEqualTo(FORBIDDEN);
    }

    private static Stream<Server> getServers() {
        return Stream.of(Server.builder().id(GOULTARD).imgUrl(URL).labels(Map.of(DEFAULT_LANGUAGE, GOULTARD)).build(),
                Server.builder().id(DJAUL).labels(Map.of(DEFAULT_LANGUAGE, DJAUL)).build(),
                Server.builder().id(JIVA).build());
    }

    private static Stream<ExternalServerDto> getExternalServers() {
        return Stream.of(ExternalServerDto.builder()
                .id(GOULTARD)
                .image(URL)
                .labels(Map.of(Language.FR, GOULTARD, Language.EN, GOULTARD, Language.ES, GOULTARD))
                .build());
    }

    private static Stream<ExternalServerDto> getExternalServersWithMissingLabel() {
        return Stream.of(ExternalServerDto.builder()
                .id(GOULTARD)
                .image(URL)
                .labels(Map.of(Language.FR, GOULTARD, Language.EN, GOULTARD))
                .build(),
                ExternalServerDto.builder()
                        .id(GOULTARD)
                        .image(URL)
                        .labels(Map.of(Language.FR, GOULTARD))
                        .build(),
                ExternalServerDto.builder()
                        .id(GOULTARD)
                        .image(URL)
                        .labels(Map.of(Language.FR, GOULTARD))
                        .build(),
                ExternalServerDto.builder()
                        .id(GOULTARD)
                        .image(URL)
                        .labels(Collections.emptyMap())
                        .build(),
                ExternalServerDto.builder()
                        .id(GOULTARD)
                        .image(URL)
                        .build());
    }
}