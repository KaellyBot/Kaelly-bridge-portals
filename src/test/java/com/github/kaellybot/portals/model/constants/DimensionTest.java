package com.github.kaellybot.portals.model.constants;

import com.github.kaellybot.portals.test.TranslatorLoader;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class DimensionTest {

    private final static List<Properties> LABELS = TranslatorLoader.loadLabels();

    @ParameterizedTest
    @EnumSource(Dimension.class)
    void isLabelPresentTest(Dimension dimension){
        LABELS.forEach(
                prop -> assertAll(
                        () -> assertNotNull(prop.getProperty(dimension.getKey())),
                        () -> assertFalse(prop.getProperty(dimension.getKey()).trim().isEmpty())
                )
        );
    }
}
