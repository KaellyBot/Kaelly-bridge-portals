package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.entity.Author;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuthorMapperTest {

    // Platform
    private static final String DISCORD = "Discord";
    private static final String DOFUS_PORTALS = "Dofus-portals";
    private static final String DIMTOPIA = "Dimtopia";

    // Persona
    private static final String OSGL = "Osgl";
    private static final String CHIRON = "Chiron";
    private static final String BLANCIX = "Blancix";
    private static final String KIZARD = "Kizard";
    private static final String GRABUGE = "Grabuge";
    private static final String SONGFU = "Songfu";

    @Autowired
    private AuthorMapper authorMapper;

    @ParameterizedTest
    @MethodSource("getAuthors")
    void mapPositionDtoTest(Author author)
    {
        assertThat(authorMapper.map(author)).isNotNull();
        assertThat(author.getName()).isNotNull().isEqualTo(authorMapper.map(author).getName());
        assertThat(author.getPlatform()).isNotNull().isEqualTo(authorMapper.map(author).getPlatform());
    }

    private static Stream<Author> getAuthors() {
        return Stream.of(Author.builder().name(OSGL).platform(DISCORD).build(),
                Author.builder().name(BLANCIX).platform(DISCORD).build(),
                Author.builder().name(SONGFU).platform(DOFUS_PORTALS).build(),
                Author.builder().name(GRABUGE).platform(DOFUS_PORTALS).build(),
                Author.builder().name(CHIRON).platform(DIMTOPIA).build(),
                Author.builder().name(KIZARD).platform(DIMTOPIA).build());
    }
}
