package com.github.kaellybot.portals.model.constants;

import com.github.kaellybot.portals.util.Translator;

public enum TransportArea {

    TRANSPORT_PLAINES_CANIA("transport.plaines_cania"),
    TRANSPORT_AREA_AMAKNA("transport.area.amakna"),
    TRANSPORT_ASTRUB("transport.astrub"),
    TRANSPORT_BAIE_SUFOKIA("transport.baie_sufokia"),
    TRANSPORT_BONTA("transport.bonta"),
    TRANSPORT_ILE_OTOMAI("transport.ile_otomai"),
    TRANSPORT_ILE_FRIGOST("transport.ile_frigost"),
    TRANSPORT_ILE_MOON("transport.ile_moon"),
    TRANSPORT_ILE_WABBIT("transport.ile_wabbit"),
    TRANSPORT_LANDES_SIDIMOTE("transport.landes_sidimote"),
    TRANSPORT_BRAKMAR("transport.brakmar"),
    TRANSPORT_VILLAGE_ZOTHS("transport.village_zoths"),
    TRANSPORT_VILLAGE_BRIGANDINS("transport.village_brigandins"),
    TRANSPORT_TERRITOIRE_DOPEULS("transport.territoire_dopeuls"),
    TRANSPORT_TAINELA("transport.tainela"),
    TRANSPORT_SAHARACH("transport.saharach"),
    TRANSPORT_PANDALA_TERRE("transport.pandala_terre"),
    TRANSPORT_PANDALA_NEUTRE("transport.pandala_neutre"),
    TRANSPORT_PANDALA_FEU("transport.pandala_feu"),
    TRANSPORT_PANDALA_EAU("transport.pandala_eau"),
    TRANSPORT_PANDALA_AIR("transport.pandala_air"),
    TRANSPORT_MONTAGNE_KOALAKS("transport.montagne_koalaks"),

    TODO("todo");

    private String key;

    TransportArea(String key){
        this.key = key;
    }

    private String getKey(){
        return key;
    }

    public String getLabel(Language lang){
        return Translator.getLabel(lang, getKey());
    }
}
