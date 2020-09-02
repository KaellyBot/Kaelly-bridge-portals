package com.github.kaellybot.portals.mapper;

import com.github.kaellybot.commons.model.constants.Language;
import com.github.kaellybot.commons.model.entity.Dimension;
import com.github.kaellybot.commons.util.Translator;
import com.github.kaellybot.portals.model.dto.DimensionDto;
import com.github.kaellybot.portals.model.dto.ExternalDimensionDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DimensionMapper {

    private final Translator translator;

    public DimensionDto map(Dimension dimension, Language language){
        return DimensionDto.builder()
                .id(dimension.getId())
                .name(translator.getLabel(language, dimension))
                .color(dimension.getColor())
                .image(dimension.getUrlImg())
                .build();
    }

    public Dimension map(ExternalDimensionDto dimension){
        return Dimension.builder()
                .id(dimension.getId())
                .labels(dimension.getLabels())
                .color(dimension.getColor())
                .urlImg(dimension.getImage())
                .build();
    }
}