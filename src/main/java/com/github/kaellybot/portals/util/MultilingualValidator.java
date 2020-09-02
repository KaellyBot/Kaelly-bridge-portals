package com.github.kaellybot.portals.util;

import com.github.kaellybot.commons.model.constants.Language;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class MultilingualValidator implements ConstraintValidator<Multilingual, Map<Language, String>> {

    @Override
    public boolean isValid(Map<Language, String> value, ConstraintValidatorContext context) {
        return Optional.ofNullable(value)
                .flatMap(labels -> Stream.of(Language.values())
                        .filter(Language::isDisplayed)
                        .map(language -> labels.containsKey(language) && validateLabel(labels.get(language)))
                        .reduce(Boolean::logicalAnd))
                .orElse(false);
    }

    private boolean validateLabel(String label){
        return label != null && ! label.trim().isEmpty();
    }
}
