package com.github.kaellybot.portals.model.constants;

import com.github.kaellybot.portals.test.TranslatorLoader;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class SubAreaTest {

    private final static List<Properties> LABELS = TranslatorLoader.loadLabels();

    @ParameterizedTest
    @EnumSource(SubArea.class)
    void isLabelPresentTest(SubArea subArea){
        LABELS.forEach(
                prop -> assertAll(
                        () -> assertNotNull(prop.getProperty(subArea.getKey())),
                        () -> assertFalse(prop.getProperty(subArea.getKey()).trim().isEmpty())
                )
        );
    }
}
