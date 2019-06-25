package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.controller.PortalConstants;
import com.github.kaellybot.portals.model.constants.Language;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class LanguageService implements ILanguageService {

    @Override
    public Optional<Language> findByName(String name) {
        if (name == null || name.trim().isEmpty())
            return Optional.of(PortalConstants.DEFAULT_LANGUAGE);
        final String NORMALIZED_NAME = StringUtils.stripAccents(name.toUpperCase().trim());
        return Stream.of(Language.values())
                .filter(lang -> StringUtils.stripAccents(lang.name().toUpperCase().trim())
                        .equals(NORMALIZED_NAME))
                .findFirst();
    }
}