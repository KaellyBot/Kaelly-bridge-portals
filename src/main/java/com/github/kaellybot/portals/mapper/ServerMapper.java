package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.commons.model.constants.Language;
import com.github.kaellybot.commons.model.entity.Server;
import com.github.kaellybot.commons.util.Translator;
import com.github.kaellybot.portals.model.dto.ServerDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ServerMapper {

    private final Translator translator;

    public ServerDto map(Server server, Language language){
        return ServerDto.builder()
                .id(server.getId())
                .name(translator.getLabel(language, server))
                .image(server.getImgUrl())
                .build();
    }
}