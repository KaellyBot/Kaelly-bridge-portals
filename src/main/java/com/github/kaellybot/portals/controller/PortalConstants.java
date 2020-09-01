package com.github.kaellybot.portals.controller;

import com.github.kaellybot.commons.model.constants.Language;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.github.kaellybot.commons.model.constants.Language.FR;

public final class PortalConstants {

    private PortalConstants(){}

    public static final Language DEFAULT_LANGUAGE = FR;
    static final String API = "/api";
    static final String SERVER_VAR = "server";
    static final String DIMENSION_VAR = "dimension";

    static final String PORTAL_FIND_BY_ID    = "/servers/{" + SERVER_VAR + "}/portals/{" + DIMENSION_VAR + "}";
    static final String PORTAL_FIND_ALL      = "/servers/{" + SERVER_VAR + "}/portals";
    static final String PORTAL_MERGE         = "/servers/{" + SERVER_VAR + "}/portals/{" + DIMENSION_VAR + "}";
    static final String SERVER_FIND_BY_ID    = "/servers/{" + SERVER_VAR + "}";
    static final String SERVER_FIND_ALL      = "/servers";
    static final String DIMENSION_FIND_BY_ID = "/dimensions/{" + DIMENSION_VAR + "}";
    static final String DIMENSION_FIND_ALL   = "/dimensions";

    static final String SERVER_NOT_FOUND_MESSAGE = "The specified server is not found.";
    static final String DIMENSION_NOT_FOUND_MESSAGE = "The specified dimension is not found.";

    static final ResponseStatusException SERVER_NOT_FOUND = new ResponseStatusException(
            HttpStatus.NOT_FOUND, SERVER_NOT_FOUND_MESSAGE);
    static final ResponseStatusException DIMENSION_NOT_FOUND = new ResponseStatusException(
            HttpStatus.NOT_FOUND, DIMENSION_NOT_FOUND_MESSAGE);
}