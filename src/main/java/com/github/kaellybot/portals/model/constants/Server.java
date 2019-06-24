package com.github.kaellybot.portals.model.constants;

public enum Server {

    AGRIDE("server.agride"),
    ATCHAM("server.atcham"),
    CROCABULIA("server.crocabulia"),
    ECHO("server.echo"),
    MERIANA("server.meriana"),
    OMBRE("server.ombre"),
    OTO_MUSTAM("server.oto_mustam"),
    RUBILAX("server.rubilax"),
    PANDORE("server.pandore"),
    USH("server.ush"),
    JULITH("server.julith"),
    NIDAS("server.nidas"),
    MERKATOR("server.merkator"),
    FURYE("server.furye"),
    BRUMEN("server.brumen"),
    ILYZAELLE("server.ilyzaelle");

    private String key;

    Server(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
