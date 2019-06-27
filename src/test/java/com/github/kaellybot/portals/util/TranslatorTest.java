package com.github.kaellybot.portals.util;

import com.github.kaellybot.portals.model.constants.Language;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TranslatorTest {

    private static final String TEST_KEY = "translator.test";
    private static final String EMPTY_TEST_KEY = "translator.test.empty";
    private static final String NO_EXISTING_TEST_KEY = "translator.test_not_found";
    private static final String TEST_VALUE = "Test";

    @ParameterizedTest
    @EnumSource(Language.class)
    void translatorTest(Language language)
    {
        assertEquals(TEST_VALUE, Translator.getLabel(language, TEST_KEY));
        assertEquals(EMPTY_TEST_KEY, Translator.getLabel(language, EMPTY_TEST_KEY));
        assertEquals(NO_EXISTING_TEST_KEY, Translator.getLabel(language, NO_EXISTING_TEST_KEY));
    }
}
