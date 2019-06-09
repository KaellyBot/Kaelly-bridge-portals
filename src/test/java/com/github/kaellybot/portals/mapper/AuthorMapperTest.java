package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.entity.Author;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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
        return Stream.of(Author.builder().name("").platform("Discord").build(),
                Author.builder().name("Kaysoro").platform("Discord").build(),
                Author.builder().name("Songfu").platform("Dofus-portals").build(),
                Author.builder().name("Grabuge").platform("Dofus-portals").build(),
                Author.builder().name("Chiron").platform("Dimtopia").build(),
                Author.builder().name("Kizard").platform("Dimtopia").build());
    }
}
