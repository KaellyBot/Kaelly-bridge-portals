package com.github.kaellybot.portals.util;

import com.github.kaellybot.portals.model.constants.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(translator.getLabel(language, TEST_KEY)).isNotNull().isEqualTo(TEST_VALUE);
        assertThat(translator.getLabel(language, EMPTY_TEST_KEY)).isNotNull().isEqualTo(EMPTY_TEST_KEY);
        assertThat(translator.getLabel(language, NO_EXISTING_TEST_KEY)).isNotNull().isEqualTo(NO_EXISTING_TEST_KEY);
    }

    @ParameterizedTest
    @EnumSource(Language.class)
    void getLabelEnumerationTest(Language language)
    {
        assertThat(Server.values()).allSatisfy(enumeration -> assertThat(translator.getLabel(language, enumeration))
                .isNotNull().isNotEmpty().isNotEqualTo(enumeration.getKey()));
        assertThat(Dimension.values()).allSatisfy(enumeration -> assertThat(translator.getLabel(language, enumeration))
                .isNotNull().isNotEmpty().isNotEqualTo(enumeration.getKey()));
        assertThat(Area.values()).allSatisfy(enumeration -> assertThat(translator.getLabel(language, enumeration))
                .isNotNull().isNotEmpty().isNotEqualTo(enumeration.getKey()));
        assertThat(SubArea.values()).allSatisfy(enumeration -> assertThat(translator.getLabel(language, enumeration))
                .isNotNull().isNotEmpty().isNotEqualTo(enumeration.getKey()));
        assertThat(TransportType.values()).allSatisfy(enumeration -> assertThat(translator.getLabel(language, enumeration))
                .isNotNull().isNotEmpty().isNotEqualTo(enumeration.getKey()));

        MultilingualEnum noExistingEnum = () -> NO_EXISTING_TEST_KEY;
        assertThat(translator.getLabel(language, noExistingEnum)).isNotNull().isEqualTo(NO_EXISTING_TEST_KEY);
    }
}