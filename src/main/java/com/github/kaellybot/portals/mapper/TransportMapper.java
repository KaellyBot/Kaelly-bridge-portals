package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.portals.model.constants.Language;
import com.github.kaellybot.portals.model.constants.Transport;
import com.github.kaellybot.portals.model.dto.TransportDto;
import com.github.kaellybot.portals.util.Translator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransportMapper {

    private Translator translator;

    private PositionMapper positionMapper;

    public TransportDto map(Transport transport, Language language){
        return TransportDto.builder()
                .type(translator.getLabel(language, transport.getType()))
                .area(translator.getLabel(language, transport.getSubArea().getArea()))
                .subArea(translator.getLabel(language, transport.getSubArea()))
                .position(positionMapper.map(transport.getPosition()))
                .isAvailableUnderConditions(transport.isAvailableUnderConditions())
                .build();
    }
}