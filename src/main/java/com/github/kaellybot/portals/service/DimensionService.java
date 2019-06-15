package com.github.kaellybot.portals.service;

import com.github.kaellybot.portals.model.constants.Dimension;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class DimensionService implements IDimensionService {

    @Override
    public Optional<Dimension> findByName(String name){
        final String NORMALIZED_NAME = StringUtils.stripAccents(name.toUpperCase().trim());
        return Stream.of(Dimension.values())
                .filter(dim -> StringUtils.stripAccents(dim.getName().toUpperCase().trim())
                        .equals(NORMALIZED_NAME))
                .findFirst();
    }
}