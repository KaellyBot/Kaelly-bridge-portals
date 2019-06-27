package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.entity.Author;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.github.kaellybot.portals.test.ConstantsTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthorMapperTest {

    @ParameterizedTest
    @MethodSource("getAuthors")
    void mapPositionDtoTest(Author author)
    {
        assertNotNull(AuthorMapper.map(author));
        assertEquals(author.getName(), AuthorMapper.map(author).getName());
        assertEquals(author.getPlatform(), AuthorMapper.map(author).getPlatform());
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
