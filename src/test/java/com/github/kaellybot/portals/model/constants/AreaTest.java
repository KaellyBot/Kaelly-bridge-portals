package com.github.kaellybot.portals.model.constants;

import com.github.kaellybot.portals.test.TranslatorLoader;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class AreaTest {

    private final static List<Properties> LABELS = TranslatorLoader.loadLabels();

    @ParameterizedTest
    @EnumSource(Area.class)
    void isLabelPresentTest(Area area){
        LABELS.forEach(
                prop -> assertAll(
                        () -> assertNotNull(prop.getProperty(area.getKey())),
                        () -> assertFalse(prop.getProperty(area.getKey()).trim().isEmpty())
                )
        );
    }
}
