package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.commons.model.constants.Language;
import com.github.kaellybot.commons.model.entity.Server;
import com.github.kaellybot.portals.model.dto.ExternalServerDto;
import com.github.kaellybot.portals.model.dto.ServerDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ServerMapperTest {

    private static final Language DEFAULT_LANGUAGE = Language.EN;
    private static final String URL = "http://image.png";
    private static final String GOULTARD = "Goultard";

    @Autowired
    private ServerMapper serverMapper;

    @ParameterizedTest
    @MethodSource("getServers")
    void mapServerDtoTest(Server server)
    {
        ServerDto result = serverMapper.map(server, DEFAULT_LANGUAGE);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull().isEqualTo(server.getId());
        assertThat(result.getName()).isNotNull().isEqualTo(server.getLabels().get(DEFAULT_LANGUAGE));
        assertThat(result.getImage()).isNotNull().isEqualTo(server.getImgUrl());
    }

    @ParameterizedTest
    @MethodSource("getExternalServers")
    void mapExternalServerDtoTest(ExternalServerDto server)
    {
        Server result = serverMapper.map(server);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull().isEqualTo(server.getId());
        assertThat(result.getLabels()).isNotNull().isEqualTo(server.getLabels());
        assertThat(result.getImgUrl()).isNotNull().isEqualTo(server.getImage());
    }

    private static Stream<Server> getServers() {
        return Stream.of(Server.builder().id(GOULTARD).imgUrl(URL).labels(Map.of(DEFAULT_LANGUAGE, GOULTARD)).build());
    }

    private static Stream<ExternalServerDto> getExternalServers() {
        return Stream.of(ExternalServerDto.builder().id(GOULTARD).image(URL).labels(Map.of(DEFAULT_LANGUAGE, GOULTARD)).build());
    }
}
