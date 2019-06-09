package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.entity.Author;
import com.github.kaellybot.portals.model.dto.AuthorDto;

public class AuthorMapper {

    public static AuthorDto map(Author author){
        return new AuthorDto()
                .withName(author.getName())
                .withPlatform(author.getPlatform());
    }
}
