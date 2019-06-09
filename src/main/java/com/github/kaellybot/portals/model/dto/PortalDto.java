package com.github.kaellybot.portals.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder(toBuilder = true)
public class PortalDto {

    private String dimension;
    private PositionDto position;
    private Boolean isAvailable;
    private Integer utilisation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Instant creationDate;
    private AuthorDto creationAuthor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Instant lastUpdateDate;
    private AuthorDto lastAuthorUpdate;
    private TransportDto nearestZaap;
    private TransportDto nearestTransportLimited;
}