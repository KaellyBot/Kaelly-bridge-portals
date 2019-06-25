package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.controller.PortalConstants;
import com.github.kaellybot.portals.model.constants.Language;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LanguageServiceTest {

    @Autowired
    private ILanguageService languageService;

    @ParameterizedTest
    @EnumSource(Language.class)
    void findPassingCaseTest(Language language){
        assertAll(
                () -> assertTrue(languageService.findByName(language.name()).isPresent()),
                () -> assertTrue(languageService.findByName(language.name().toLowerCase()).isPresent()),
                () -> assertTrue(languageService.findByName(language.name().toUpperCase()).isPresent()),
                () -> assertTrue(languageService.findByName(StringUtils.stripAccents(language.name())).isPresent())
        );

        languageService.findByName(language.name())
                .ifPresent(potentialServer -> assertEquals(language, potentialServer));
    }

    @ParameterizedTest
    @EnumSource(Language.class)
    void findNoPassingCaseTest(Language language){
        assertFalse(languageService.findByName(language.name() + "_BAD_NAME").isPresent());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void findNullAndEmptyLanguageTest(String source){
        languageService.findByName(source)
                .ifPresent(lang -> assertEquals(PortalConstants.DEFAULT_LANGUAGE, lang));
    }
}