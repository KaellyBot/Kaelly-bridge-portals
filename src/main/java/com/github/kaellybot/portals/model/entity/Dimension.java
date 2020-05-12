package com.github.kaellybot.portals.model.entity;

import com.github.kaellybot.portals.model.constants.Language;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Builder
@Document(collection = "dimensions")
public class Dimension {

    @Id
    private String id;
    private Map<Language, String> translation;
}
