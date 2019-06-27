package com.github.kaellybot.portals.util;

import com.github.kaellybot.portals.model.constants.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import static com.github.kaellybot.portals.controller.PortalConstants.DEFAULT_LANGUAGE;

public class Translator {

    private final static Logger LOG = LoggerFactory.getLogger(Translator.class);
    private static Map<Language, Properties> labels;

    public synchronized static String getLabel(Language lang, String property){
        if (labels == null){
            labels = new ConcurrentHashMap<>();

            for(Language language : Language.values())
                try(InputStream file = Translator.class.getResourceAsStream("/label_" + language.name())) {
                    Properties prop = new Properties();
                    prop.load(new BufferedReader(new InputStreamReader(file, StandardCharsets.UTF_8)));
                    labels.put(language, prop);
                } catch (IOException e) {
                    LOG.error("Translator.getLabel", e);
                }
        }

        String value = labels.get(lang).getProperty(property);
        if (value == null || value.trim().isEmpty())
            if (DEFAULT_LANGUAGE != lang) {
                LOG.error("Missing label in " + lang.name() + " : " + property);
                return getLabel(DEFAULT_LANGUAGE, property);
            }
            else
                return property;
        return value;
    }
}
