package com.github.kaellybot.portals.model.constants;

import com.github.kaellybot.portals.util.Translator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransportType {

    TRANSPORT_TYPE_ZAAP("transport.type.zaap"),
    TRANSPORT_TYPE_FOREUSE("transport.type.foreuse"),
    TRANSPORT_TYPE_DILIGENCE("transport.type.diligence"),
    TRANSPORT_TYPE_BRIGANDIN("transport.type.brigandin"),
    TRANSPORT_TYPE_TRANSPORTEUR_FRIGOSTIEN("transport.type.transporteur_frigostien"),
    TRANSPORT_TYPE_SKI("transport.type.ski"),
    TRANSPORT_TYPE_SCAEROPLANE("transport.type.scaeroplane");

    private String key;

    public String getLabel(Language lang){
        return Translator.getLabel(lang, getKey());
    }
}