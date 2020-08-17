package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.commons.model.constants.Language;
import com.github.kaellybot.commons.util.Translator;
import com.github.kaellybot.portals.model.constants.Transport;
import com.github.kaellybot.portals.model.dto.TransportDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransportMapper {

    private final Translator translator;

    private final PositionMapper positionMapper;

    public TransportDto map(Transport transport, Language language){
        return TransportDto.builder()
                .type(translator.getLabel(language, transport.getType().getKey()))
                .area(translator.getLabel(language, transport.getSubArea().getArea().getKey()))
                .subArea(translator.getLabel(language, transport.getSubArea().getKey()))
                .position(positionMapper.map(transport.getPosition()))
                .isAvailableUnderConditions(transport.isAvailableUnderConditions())
                .build();
    }
}