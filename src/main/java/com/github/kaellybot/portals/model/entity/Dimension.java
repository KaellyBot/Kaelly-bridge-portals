package com.github.kaellybot.portals.model.entity;

import com.github.kaellybot.portals.model.constants.Language;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "dimensions")
public class Dimension extends MultilingualEntity {

    @Builder
    public Dimension(String id, Map<Language, String> labels){
        super(id, labels);
    }
}
