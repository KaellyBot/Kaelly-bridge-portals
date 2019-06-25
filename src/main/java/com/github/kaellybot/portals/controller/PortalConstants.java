package com.github.kaellybot.portals.controller;

import com.github.kaellybot.portals.model.constants.Language;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.github.kaellybot.portals.model.constants.Language.FR;

public final class PortalConstants {

    private PortalConstants(){}

    public static final Language DEFAULT_LANGUAGE = FR;
    static final String API = "/api";
    static final String PORTALS = "/portals";
    static final String TOKEN_VAR = "token";
    static final String LANGUAGE_VAR = "lang";
    static final String DIMENSION_VAR = "dimension";
    static final String SERVER_VAR = "{server}";
    static final String LANGUAGE_NOT_FOUND_MESSAGE = "The specified language is not managed.";
    static final String SERVER_NOT_FOUND_MESSAGE = "The specified server is not found.";
    static final String DIMENSION_NOT_FOUND_MESSAGE = "The specified dimension is not found.";
    static final String INTERNAL_SERVER_ERROR_MESSAGE = "A technical error occurred. Sorry for the inconvenience.";

    static final ResponseStatusException LANGUAGE_NOT_FOUND = new ResponseStatusException(
            HttpStatus.NOT_FOUND, LANGUAGE_NOT_FOUND_MESSAGE);
    static final ResponseStatusException SERVER_NOT_FOUND = new ResponseStatusException(
            HttpStatus.NOT_FOUND, SERVER_NOT_FOUND_MESSAGE);
    static final ResponseStatusException DIMENSION_NOT_FOUND = new ResponseStatusException(
            HttpStatus.NOT_FOUND, DIMENSION_NOT_FOUND_MESSAGE);
    static final ResponseStatusException INTERNAL_SERVER_ERROR = new ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MESSAGE);
}