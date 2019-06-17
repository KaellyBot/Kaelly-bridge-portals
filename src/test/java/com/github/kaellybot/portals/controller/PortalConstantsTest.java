package com.github.kaellybot.portals.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.github.kaellybot.portals.controller.PortalConstants.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PortalConstantsTest {

    @Test
    void serverNotFoundExceptionTest(){
        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, SERVER_NOT_FOUND.getStatus()),
                () -> assertEquals(SERVER_NOT_FOUND_MESSAGE, SERVER_NOT_FOUND.getReason())
        );
    }

    @Test
    void dimensionNotFoundExceptionTest(){
        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, DIMENSION_NOT_FOUND.getStatus()),
                () -> assertEquals(DIMENSION_NOT_FOUND_MESSAGE, DIMENSION_NOT_FOUND.getReason())
        );
    }

    @Test
    void internalServerErrorExceptionTest(){
        assertAll(
                () -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.getStatus()),
                () -> assertEquals(INTERNAL_SERVER_ERROR_MESSAGE, INTERNAL_SERVER_ERROR.getReason())
        );
    }
}