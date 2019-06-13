package com.github.kaellybot.portals.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

final class PortalConstants {

    private PortalConstants(){}

    static final String API = "/api";
    static final String PORTALS = "/portals";
    static final String TOKEN_VAR = "token";
    static final String DIMENSION_VAR = "dimension";
    static final String SERVER_VAR = "{server}";

    static final ResponseStatusException SERVER_NOT_FOUND = new ResponseStatusException(
            HttpStatus.NOT_FOUND, "The specified server is not found.");
    static final ResponseStatusException DIMENSION_NOT_FOUND = new ResponseStatusException(
            HttpStatus.NOT_FOUND, "The specified dimension is not found.");
    static final ResponseStatusException INTERNAL_SERVER_ERROR = new ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR, "A technical error occurred. Sorry for the inconvenience.");
}