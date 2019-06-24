package com.github.kaellybot.portals.model.constants;

import com.github.kaellybot.portals.test.TranslatorLoader;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    private final static List<Properties> LABELS = TranslatorLoader.loadLabels();

    @ParameterizedTest
    @EnumSource(Server.class)
    void isLabelPresentTest(Server server){
        LABELS.forEach(
                prop -> assertAll(
                        () -> assertNotNull(prop.getProperty(server.getKey())),
                        () -> assertFalse(prop.getProperty(server.getKey()).trim().isEmpty())
                )
        );
    }
}
