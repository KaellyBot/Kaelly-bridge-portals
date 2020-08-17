package com.github.kaellybot.portals.controller;

import com.github.kaellybot.commons.model.constants.Language;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.github.kaellybot.commons.model.constants.Language.FR;

public final class PortalConstants {

    private PortalConstants(){}

    public static final Language DEFAULT_LANGUAGE = FR;
    static final String API = "/api/servers/";
    static final String SERVER_VAR = "server";
    static final String DIMENSION_VAR = "dimension";
    static final String FIND_BY_ID = "{" + SERVER_VAR + "}/portals/{" + DIMENSION_VAR + "}";
    static final String FIND_ALL = "{" + SERVER_VAR + "}/portals";
    static final String MERGE = "{" + SERVER_VAR + "}/portals/{" + DIMENSION_VAR + "}";

    static final String LANGUAGE_NOT_FOUND_MESSAGE = "The specified language is not managed.";
    static final String SERVER_NOT_FOUND_MESSAGE = "The specified server is not found.";
    static final String DIMENSION_NOT_FOUND_MESSAGE = "The specified dimension is not found.";

    static final ResponseStatusException LANGUAGE_NOT_FOUND = new ResponseStatusException(
            HttpStatus.NOT_FOUND, LANGUAGE_NOT_FOUND_MESSAGE);
    static final ResponseStatusException SERVER_NOT_FOUND = new ResponseStatusException(
            HttpStatus.NOT_FOUND, SERVER_NOT_FOUND_MESSAGE);
    static final ResponseStatusException DIMENSION_NOT_FOUND = new ResponseStatusException(
            HttpStatus.NOT_FOUND, DIMENSION_NOT_FOUND_MESSAGE);
}