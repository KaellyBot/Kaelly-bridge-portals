package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.controller.PortalConstants;
import com.github.kaellybot.portals.model.constants.Language;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LanguageServiceTest {

    @Autowired
    private LanguageService languageService;

    @ParameterizedTest
    @EnumSource(Language.class)
    void findPassingCaseTest(Language language){
        assertAll(
                () -> assertThat(languageService.findByName(language.name())).isPresent(),
                () -> assertThat(languageService.findByName(language.name().toLowerCase())).isPresent(),
                () -> assertThat(languageService.findByName(language.name().toUpperCase())).isPresent(),
                () -> assertThat(languageService.findByName(StringUtils.stripAccents(language.name()))).isPresent()
        );

        languageService.findByName(language.name())
                .ifPresent(potentialServer -> assertThat(language).isNotNull().isEqualTo(potentialServer));
    }

    @ParameterizedTest
    @EnumSource(Language.class)
    void findNoPassingCaseTest(Language language){
        assertThat(languageService.findByName(language.name() + "_BAD_NAME")).isEmpty();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void findNullAndEmptyLanguageTest(String source){
        languageService.findByName(source)
                .ifPresent(lang -> assertThat(PortalConstants.DEFAULT_LANGUAGE).isNotNull().isEqualTo(lang));
    }
}