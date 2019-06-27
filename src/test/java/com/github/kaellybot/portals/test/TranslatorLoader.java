package com.github.kaellybot.portals.test;

import com.github.kaellybot.portals.model.constants.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TranslatorLoader {

    private static final Logger LOG = LoggerFactory.getLogger(TranslatorLoader.class);

    public static List<Properties> loadLabels(){
        List<Properties> result = new ArrayList<>();
        for (Language lg : Language.values())
            try(InputStream file = TranslatorLoader.class.getResourceAsStream("/label_" + lg + ".properties")) {
                Properties prop = new Properties();
                prop.load(new BufferedReader(new InputStreamReader(file, StandardCharsets.UTF_8)));
                result.add(prop);
            } catch (IOException e) {
                LOG.error("TranslatorLoader.loadLabels", e);
            }
        return result;
    }
}
