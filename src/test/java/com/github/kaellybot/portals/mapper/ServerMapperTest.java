package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.commons.model.constants.Language;
import com.github.kaellybot.commons.model.entity.Server;
import com.github.kaellybot.portals.model.dto.ServerDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ServerMapperTest {

    private static final Language DEFAULT_LANGUAGE = Language.EN;
    private static final String URL = "http://image.png";
    private static final String GOULTARD = "Goultard";
    private static final String DJAUL = "Djaul";

    @Autowired
    private ServerMapper serverMapper;

    @ParameterizedTest
    @MethodSource("getServers")
    void mapPositionDtoTest(Server server)
    {
        ServerDto result = serverMapper.map(server, DEFAULT_LANGUAGE);
        assertThat(result).isNotNull();
        if (Optional.ofNullable(server.getLabels()).map(map -> map.containsKey(DEFAULT_LANGUAGE)).orElse(false))
            assertThat(result.getName()).isNotNull().isEqualTo(server.getLabels().get(DEFAULT_LANGUAGE));
        else
            assertThat(result.getName()).isNull();

        if (server.getImgUrl() != null)
            assertThat(result.getImage()).isNotNull().isEqualTo(server.getImgUrl());
        else
            assertThat(result.getImage()).isNull();
    }

    private static Stream<Server> getServers() {
        return Stream.of(Server.builder().imgUrl(URL).labels(Map.of(DEFAULT_LANGUAGE, GOULTARD)).build(),
                Server.builder().labels(Map.of(DEFAULT_LANGUAGE, DJAUL)).build(),
                Server.builder().build());
    }
}
