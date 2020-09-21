package com.github.kaellybot.portals.util;

import com.github.kaellybot.commons.model.constants.Language;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class MultilingualValidatorTest {

    private final MultilingualValidator multilingualValidator = new MultilingualValidator();

    @ParameterizedTest
    @MethodSource("getRightLabels")
    void isValidRightLabelsSourceTest(Map<Language, String> labels){
        assertThat(multilingualValidator.isValid(labels, null)).isTrue();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @MethodSource("getWrongLabels")
    void isValidWrongLabelsSourceTest(Map<Language, String> labels){
        assertThat(multilingualValidator.isValid(labels, null)).isFalse();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void validateLabelNullAndEmptySourceTest(String label){
        assertThat(multilingualValidator.validateLabel(label)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "c"})
    void validateLabelHappyPath(String label){
        assertThat(multilingualValidator.validateLabel(label)).isTrue();
    }

    private static Stream<Map<Language, String>> getWrongLabels() {
        return Stream.of(Map.of(Language.FR, "test"),
                Map.of(Language.FR, "test", Language.EN, "test"),
                Map.of(Language.APRIL_FOOL, "test"));
    }

    private static Stream<Map<Language, String>> getRightLabels() {
        return Stream.of(Map.of(Language.FR, "test", Language.EN, "test", Language.ES, "test"));
    }
}
