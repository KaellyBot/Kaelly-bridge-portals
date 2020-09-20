package com.github.kaellybot.portals.controller;

import com.github.kaellybot.commons.model.constants.Language;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.github.kaellybot.commons.model.constants.Language.FR;

public final class PortalConstants {

    private PortalConstants(){}

    public static final Language DEFAULT_LANGUAGE = FR;
    public static final String API = "/api";

    private static final String SERVER_RESOURCE    = "servers";
    private static final String DIMENSION_RESOURCE = "dimensions";
    private static final String TOKEN_RESOURCE = "tokens";
    private static final String PORTAL_RESOURCE    = "portals";
    static final String SERVER_VAR    = "server";
    static final String DIMENSION_VAR = "dimension";
    static final String TOKEN_VAR = "token";

    public static final String PORTAL_FIND_BY_ID    = "/" + SERVER_RESOURCE + "/{" + SERVER_VAR + "}/" + PORTAL_RESOURCE + "/{" + DIMENSION_VAR + "}";
    public static final String PORTAL_FIND_ALL      = "/" + SERVER_RESOURCE + "/{" + SERVER_VAR + "}/" + PORTAL_RESOURCE;
    public static final String PORTAL_MERGE         = "/" + SERVER_RESOURCE + "/{" + SERVER_VAR + "}/" + PORTAL_RESOURCE + "/{" + DIMENSION_VAR + "}";
    public static final String SERVER_FIND_BY_ID    = "/" + SERVER_RESOURCE + "/{" + SERVER_VAR + "}";
    public static final String SERVER_FIND_ALL      = "/" + SERVER_RESOURCE;
    public static final String SERVER_SAVE          = "/" + SERVER_RESOURCE;
    public static final String DIMENSION_FIND_BY_ID = "/" + DIMENSION_RESOURCE + "/{" + DIMENSION_VAR + "}";
    public static final String DIMENSION_FIND_ALL   = "/" + DIMENSION_RESOURCE;
    public static final String DIMENSION_SAVE       = "/" + DIMENSION_RESOURCE;
    public static final String TOKEN_FIND_ALL = "/" + TOKEN_RESOURCE;
    public static final String TOKEN_SAVE       = "/" + TOKEN_RESOURCE;
    public static final String TOKEN_DELETE   = "/" + TOKEN_RESOURCE + "/{" + TOKEN_VAR + "}";

    public static final String USERNAME_NOT_FOUND_MESSAGE = "The username is mandatory.";
    public static final String PASSWORD_NOT_FOUND_MESSAGE = "The password is mandatory.";
    public static final String PRIVILEGE_NOT_FOUND_MESSAGE = "At least one privilege is mandatory";
    public static final String USER_TYPE_NOT_FOUND_MESSAGE = "At least one privilege is mandatory";
    public static final String ID_NOT_FOUND_MESSAGE = "The ID is mandatory.";
    public static final String IMAGE_NOT_FOUND_MESSAGE = "The image is mandatory.";
    static final String SERVER_NOT_FOUND_MESSAGE = "The specified server is not found.";
    static final String DIMENSION_NOT_FOUND_MESSAGE = "The specified dimension is not found.";
    static final String TOKEN_ALREADY_EXISTS_MESSAGE = "A token with the same username already exists.";

    static final ResponseStatusException SERVER_NOT_FOUND = new ResponseStatusException(
            HttpStatus.NOT_FOUND, SERVER_NOT_FOUND_MESSAGE);
    static final ResponseStatusException DIMENSION_NOT_FOUND = new ResponseStatusException(
            HttpStatus.NOT_FOUND, DIMENSION_NOT_FOUND_MESSAGE);
    static final ResponseStatusException TOKEN_ALREADY_EXISTS = new ResponseStatusException(
            HttpStatus.BAD_REQUEST, TOKEN_ALREADY_EXISTS_MESSAGE);
}