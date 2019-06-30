package com.github.kaellybot.portals.model.constants;

import com.github.kaellybot.portals.util.Translator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransportType {

    ZAAP        ("transport_type.zaap"                   ),
    FOREUSE     ("transport_type.foreuse"                ),
    DILIGENCE   ("transport_type.diligence"              ),
    BRIGANDIN   ("transport_type.brigandin"              ),
    FRIGOSTIEN  ("transport_type.transporteur_frigostien"),
    SKIS        ("transport_type.skis"                   ),
    SCAEROPLANE ("transport_type.scaeroplane"            ),
    CHAR_A_VOILE("transport_type.char_a_voile"           );

    private String key;

    public String getLabel(Language lang){
        return Translator.getLabel(lang, getKey());
    }
}