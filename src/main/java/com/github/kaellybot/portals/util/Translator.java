package com.github.kaellybot.portals.util;

import com.github.kaellybot.portals.model.constants.Language;
import com.github.kaellybot.portals.model.constants.MultilingualEnum;
import com.github.kaellybot.portals.model.entity.MultilingualEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Translator {

    private static final Logger LOG = LoggerFactory.getLogger(Translator.class);
    private final Map<Language, Properties> labels;

    public Translator(){
        labels = new ConcurrentHashMap<>();

        for(Language lg : Language.values())
            try(InputStream file = Translator.class.getResourceAsStream("/label_" + lg + ".properties")) {
                Properties prop = new Properties();
                prop.load(new BufferedReader(new InputStreamReader(file, StandardCharsets.UTF_8)));
                labels.put(lg, prop);
            } catch (IOException e) {
                LOG.error("Translator.getLabel", e);
            }
    }

    public String getLabel(Language lang, String property){
        String value = labels.get(lang).getProperty(property);
        if (value == null || value.trim().isEmpty()) {
            LOG.warn("Missing label in {} for property '{}'", lang, property);
            return property;
        }

        return value;
    }

    public String getLabel(Language lang, MultilingualEnum anEnum){
        String value = labels.get(lang).getProperty(anEnum.getKey());
        if (value == null || value.trim().isEmpty()) {
            LOG.warn("Missing label in {} for enum {}[{}]", lang, anEnum.getClass().getSimpleName(), anEnum);
            return anEnum.getKey();
        }

        return value;
    }

    public String getLabel(Language lang, MultilingualEntity entity){
        if (! Optional.ofNullable(entity.getLabels()).map(map -> map.containsKey(lang)).orElse(false)) {
            LOG.warn("Missing label in {} for entity {}[{}]", lang, entity.getClass().getSimpleName(), entity.getId());
            return entity.getId();
        }

        return entity.getLabels().get(lang);
    }
}