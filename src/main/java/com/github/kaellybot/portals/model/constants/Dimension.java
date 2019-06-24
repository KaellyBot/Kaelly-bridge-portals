package com.github.kaellybot.portals.model.constants;

public enum Dimension {

    ENUTROSOR("dimension.enutrosor"),
    SRAMBAD("dimension.srambad"),
    XELORIUM("dimension.xelorium"),
    ECAFLIPUS("dimension.ecaflipus");

    private String key;

    Dimension(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
