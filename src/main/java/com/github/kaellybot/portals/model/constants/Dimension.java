package com.github.kaellybot.portals.model.constants;

import com.github.kaellybot.portals.util.Translator;

public enum Dimension {

    ENUTROSOR("dimension.enutrosor"),
    SRAMBAD("dimension.srambad"),
    XELORIUM("dimension.xelorium"),
    ECAFLIPUS("dimension.ecaflipus");

    private String key;

    Dimension(String key){
        this.key = key;
    }

    String getKey() {
        return key;
    }

    public String getLabel(Language lang){
        return Translator.getLabel(lang, getKey());
    }
}
