package com.github.kaellybot.portals.util;

import com.github.kaellybot.portals.model.constants.Language;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TranslatorTest {

    private static final String TEST_KEY = "translator.test";
    private static final String EMPTY_TEST_KEY = "translator.test.empty";
    private static final String NO_EXISTING_TEST_KEY = "translator.test_not_found";
    private static final String TEST_VALUE = "Test";

    @Autowired
    private Translator translator;

    @ParameterizedTest
    @EnumSource(Language.class)
    void getLabelPropertyTest(Language language)
    {
        assertEquals(TEST_VALUE, translator.getLabel(language, TEST_KEY));
        assertEquals(EMPTY_TEST_KEY, translator.getLabel(language, EMPTY_TEST_KEY));
        assertEquals(NO_EXISTING_TEST_KEY, translator.getLabel(language, NO_EXISTING_TEST_KEY));
    }

    @ParameterizedTest
    @EnumSource(Language.class)
    void getLabelEnumerationTest(Language language)
    {
        assertEquals(TEST_VALUE, translator.getLabel(language, TEST_KEY));
        assertEquals(EMPTY_TEST_KEY, translator.getLabel(language, EMPTY_TEST_KEY));
        assertEquals(NO_EXISTING_TEST_KEY, translator.getLabel(language, NO_EXISTING_TEST_KEY));
    }
}
