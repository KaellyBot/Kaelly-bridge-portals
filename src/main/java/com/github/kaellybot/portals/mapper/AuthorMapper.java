package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.entity.Author;
import com.github.kaellybot.portals.model.dto.AuthorDto;

class AuthorMapper {

    static AuthorDto map(Author author){
        return AuthorDto.builder()
                .name(author.getName())
                .platform(author.getPlatform())
                .build();
    }
}
