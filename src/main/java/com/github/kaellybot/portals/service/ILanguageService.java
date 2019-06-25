package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.model.constants.Language;

import java.util.Optional;

public interface ILanguageService {

    Optional<Language> findByName(String name);
}
