package com.github.kaellybot.portals.model.entity;

import com.github.kaellybot.commons.model.constants.Language;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.util.Map;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public abstract class MultilingualEntity {

    @Id
    private final String id;
    private final Map<Language, String> labels;
}
