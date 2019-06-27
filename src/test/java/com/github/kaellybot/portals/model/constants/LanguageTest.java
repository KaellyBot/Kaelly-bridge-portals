package com.github.kaellybot.portals.model.constants;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LanguageTest {

    @ParameterizedTest
    @EnumSource(Language.class)
    void isFilePresentTest(Language language){
       assertNotNull(LanguageTest.class.getResource("/label_" + language + ".properties"));
    }
}
