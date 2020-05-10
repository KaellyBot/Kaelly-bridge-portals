package com.github.kaellybot.portals.model.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Dimension implements MultilingualEnum {

    ENUTROSOR("dimension.enutrosor"),
    SRAMBAD  ("dimension.srambad"  ),
    XELORIUM ("dimension.xelorium" ),
    ECAFLIPUS("dimension.ecaflipus");

    private String key;
}