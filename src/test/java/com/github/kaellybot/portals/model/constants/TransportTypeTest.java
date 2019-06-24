package com.github.kaellybot.portals.model.constants;

import com.github.kaellybot.portals.test.TranslatorLoader;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TransportTypeTest {

    private final static List<Properties> LABELS = TranslatorLoader.loadLabels();

    @ParameterizedTest
    @EnumSource(TransportType.class)
    void isLabelPresentTest(TransportType transportType){
        LABELS.forEach(
                prop -> assertAll(
                        () -> assertNotNull(prop.getProperty(transportType.getKey())),
                        () -> assertFalse(prop.getProperty(transportType.getKey()).trim().isEmpty())
                )
        );
    }
}
