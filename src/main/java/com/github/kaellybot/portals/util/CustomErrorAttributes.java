package com.github.kaellybot.portals.util;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
class CustomErrorAttributes extends DefaultErrorAttributes {

    private static final String MESSAGE_KEY = "message";

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {

        Map<String, Object> map = super.getErrorAttributes(request, options);
        Throwable error = getError(request);
        if (error instanceof WebExchangeBindException)
            map.replace(MESSAGE_KEY, ((WebExchangeBindException) error).getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(" ")));

        return map;
    }
}