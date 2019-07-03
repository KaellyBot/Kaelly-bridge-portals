package com.github.kaellybot.portals.model.constants;

import com.github.kaellybot.portals.model.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.github.kaellybot.portals.model.constants.SubArea.*;
import static com.github.kaellybot.portals.model.constants.SubArea.ILE_MINOTOROR;
import static com.github.kaellybot.portals.model.constants.SubArea.PANDALA_NEUTRE;
import static com.github.kaellybot.portals.model.constants.TransportType.*;
import static com.github.kaellybot.portals.model.constants.TransportType.FRIGOSTIEN;

@Getter
@AllArgsConstructor
public enum Transport {

    ZAAP_BORD_FORET_MALEFIQUE     (ZAAP        , FORET_MALEFIQUE            , Position.of(-1, 13)  , true ),
    ZAAP_CHATEAU_AMAKNA           (ZAAP        , CHATEAU_AMAKNA             , Position.of(3,-5)    , true ),
    ZAAP_COIN_BOUFTOUS            (ZAAP        , COIN_BOUFTOUS              , Position.of(5,7)     , true ),
    ZAAP_MONTAGNE_CRAQUELEURS     (ZAAP        , MONTAGNE_CRAQUELEURS       , Position.of(-5, -8)  , true ),
    ZAAP_PLAINE_SCARAFEUILLES     (ZAAP        , PLAINE_SCARAFEUILLES       , Position.of(-1,24)   , true ),
    ZAAP_PORT_MADRESTAM           (ZAAP        , PORT_MADRESTAM             , Position.of(7,-4)    , true ),
    ZAAP_VILLAGE_AMAKNA           (ZAAP        , VILLAGE_AMAKNA             , Position.of(-2,0)    , true ),
    ZAAP_CITE_ASTRUB              (ZAAP        , CITE_ASTRUB                , Position.of(5,-18)   , true ),
    ZAAP_SUFOKIA                  (ZAAP        , SUFOKIA                    , Position.of(13, 26)  , true ),
    ZAAP_TEMPLE_ALLIANCES         (ZAAP        , TEMPLE_ALLIANCES           , Position.of(13, 35)  , true ),
    ZAAP_RIVAGE_SUFOKIEN          (ZAAP        , RIVAGE_SUFOKIEN            , Position.of(10,22)   , true ),
    ZAAP_BONTA                    (ZAAP        , CENTRE_BONTA               , Position.of(-32,-56) , true ),
    ZAAP_VILLAGE_COTIER           (ZAAP        , VILLAGE_COTIER             , Position.of(-46,18)  , true ),
    ZAAP_VILLAGE_CANOPEE          (ZAAP        , VILLAGE_CANOPEE            , Position.of(-54,16)  , true ),
    ZAAP_BOURGADE                 (ZAAP        , BOURGADE                   , Position.of(-78,-41) , true ),
    ZAAP_VILLAGE_ENSEVELI         (ZAAP        , VILLAGE_ENSEVELI           , Position.of(-77,-73) , true ),
    ZAAP_PLAGE_TORTUE             (ZAAP        , PLAGE_TORTUE               , Position.of(35,12)   , true ),
    ZAAP_ILE_CAWOTTE              (ZAAP        , ILE_CAWOTTE                , Position.of(25,-4)   , true ),
    ZAAP_LABORATOIRES_ABANDONNES  (ZAAP        , LABORATOIRES_ABANDONNES    , Position.of(27,-14)  , false),
    ZAAP_ROUTE_ROULOTTES          (ZAAP        , ROUTE_ROULOTTES            , Position.of(-25,12)  , true ),
    ZAAP_TERRES_DESACREES         (ZAAP        , TERRES_DESACREES           , Position.of(-15,25)  , true ),
    ZAAP_VILLAGE_ELEVEURS         (ZAAP        , VILLAGE_ELEVEURS_KOALAK    , Position.of(-16,1)   , true ),
    ZAAP_VILLAGE_AERDALA          (ZAAP        , VILLAGE_AERDALA            , Position.of(17,-31)  , false),
    ZAAP_VILLAGE_AKWADALA         (ZAAP        , VILLAGE_AKWADALA           , Position.of(23,-22)  , false),
    ZAAP_VILLAGE_FEUDALA          (ZAAP        , VILLAGE_FEUDALA            , Position.of(29,-49)  , false),
    ZAAP_FAUBOURGS_PANDALA        (ZAAP        , FAUBOURGS_PANDALA          , Position.of(26,-37)  , true ),
    ZAAP_VILLAGE_TERRDALA         (ZAAP        , VILLAGE_TERRDALA           , Position.of(30,-38)  , false),
    ZAAP_CHAMPS_CANIA             (ZAAP        , CHAMPS_CANIA               , Position.of(-27,-36) , true ),
    ZAAP_LAC_CANIA                (ZAAP        , LAC_CANIA                  , Position.of(-3,-42)  , true ),
    ZAAP_MASSIF_CANIA             (ZAAP        , MASSIF_CANIA               , Position.of(-13,-28) , true ),
    ZAAP_PLAINE_PORKASS           (ZAAP        , PLAINE_PORKASS             , Position.of(-5,-23)  , true ),
    ZAAP_PLAINES_ROCHEUSES        (ZAAP        , PLAINES_ROCHEUSES          , Position.of(-17,-47) , true ),
    ZAAP_ROUTES_ROCAILLEUSES      (ZAAP        , ROUTES_ROCAILLEUSES        , Position.of(-20,-20) , true ),
    ZAAP_VILLAGE_KANIGS           (ZAAP        , VILLAGE_KANIGS             , Position.of(0,-56)   , false),
    ZAAP_DUNE_OSSEMENTS           (ZAAP        , DUNE_OSSEMENTS             , Position.of(15,-58)  , true ),
    ZAAP_BERCEAU                  (ZAAP        , TAINELA                    , Position.of(1,-32)   , true ),
    ZAAP_VILLAGE_DOPEULS          (ZAAP        , VILLAGE_DOPEULS            , Position.of(-34,-8)  , false),
    ZAAP_VILLAGE_BRIGANDINS       (ZAAP        , VILLAGE_BRIGANDINS         , Position.of(-16,-24) , false),
    ZAAP_VILLAGE_ZOTHS            (ZAAP        , VILLAGE_ZOTHS              , Position.of(-53,18)  , false),
    ZAAP_BRAKMAR                  (ZAAP        , CENTRE_BRAKMAR             , Position.of(-26,35)  , false),
    FOREUSE_LABORATOIRE_ABANDONNE (FOREUSE     , LABORATOIRES_ABANDONNES    , Position.of(27,-14)  , false),
    FOREUSE_PANDALA_NEUTRE        (FOREUSE     , PANDALA_NEUTRE             , Position.of(28,-30)  , false),
    FOREUSE_PLAGE_MOON            (FOREUSE     , PLAGE_TORTUE               , Position.of(36,4)    , false),
    FOREUSE_FEUILLAGE_ARBRE_HAKAM (FOREUSE     , FEUILLAGE_ARBRE_HAKAM      , Position.of(-53,17)  , false),
    FOREUSE_MINE_MAKSAGE          (FOREUSE     , FORET_PINS_PERDUS          , Position.of(-55,-64) , false),
    FOREUSE_MILIFUTAIE            (FOREUSE     , MILLIFUTAIE                , Position.of(2,4)     , false),
    FOREUSE_KARTONPATH            (FOREUSE     , TUNNEL_KARTONPATH          , Position.of(18, 11)  , false),
    FOREUSE_TERRITOIRE_PORCOS     (FOREUSE     , TERRITOIRE_PORCOS          , Position.of(0,33)    , false),
    FOREUSE_BAIE_CANIA            (FOREUSE     , BAIE_CANIA                 , Position.of(-29,-12) , false),
    FOREUSE_GROTTE_HURIE          (FOREUSE     , VALLEE_MORH_KITU           , Position.of(-18,14)  , false),
    FOREUSE_MINE_HERALE           (FOREUSE     , FORET_AMAKNA               , Position.of(5,19)    , false),
    FOREUSE_MINE_ISTAIRAMEUR      (FOREUSE     , MONTAGNE_BASSE_CRAQUELEURS , Position.of(-3,9)    , false),
    FOREUSE_DESOLATION_SIDIMOTE   (FOREUSE     , DESOLATION_SIDIMOTE        , Position.of(-23,21)  , false),
    FOREUSE_DENTS_PIERRE          (FOREUSE     , DENTS_PIERRE               , Position.of(-4,-53)  , false),
    DILIGENCE_ASTRUB              (DILIGENCE   , FORET_ASTRUB               , Position.of(0, -18)  , false),
    DILIGENCE_BAIE_CANIA          (DILIGENCE   , BAIE_CANIA                 , Position.of(-29,-12) , false),
    DILIGENCE_DOMAINE_FUNGUS      (DILIGENCE   , DOMAINE_FUNGUS             , Position.of(-12,29)  , false),
    DILIGENCE_CIMETIERE_HEROS     (DILIGENCE   , CIMETIERE_HEROS            , Position.of(-21,-58) , false),
    BRIGANDIN_RIVIERE_KAWAII      (BRIGANDIN   , RIVIERE_KAWAII             , Position.of(6,-1)    , false),
    BRIGANDIN_PORTE_BONTA         (BRIGANDIN   , CHAMPS_CANIA               , Position.of(-31,-48) , false),
    BRIGANDIN_PORTE_BRAKMAR       (BRIGANDIN   , PORTE_BRAKMAR              , Position.of(-26,29)  , false),
    BRIGANDIN_VILLAGE_BWORKS      (BRIGANDIN   , VILLAGE_BWORKS             , Position.of(-1,8)    , false),
    BRIGANDIN_PRESQUILE_DRAGOEUFS (BRIGANDIN   , PRESQUILE_DRAGOEUF         , Position.of(-3,27)   , false),
    BRIGANDIN_VILLAGE_BRIGANDINS  (BRIGANDIN   , VILLAGE_BRIGANDINS         , Position.of(-17,-26) , false),
    BRIGANDIN_PENINSULE_GELEES    (BRIGANDIN   , PENINSULE_GELEES           , Position.of(11,30)   , false),
    BRIGANDIN_PANDALA             (BRIGANDIN   , PANDALA_NEUTRE             , Position.of(18,-39)  , false),
    BRIGANDIN_ILE_MINOTOROR       (BRIGANDIN   , ILE_MINOTOROR              , Position.of(-41,-18) , false),
    BRIGANDIN_ILE_MOON            (BRIGANDIN   , PLAGE_TORTUE               , Position.of(35,4)    , false),
    BRIGANDIN_ILE_WABBITS         (BRIGANDIN   , ILE_CAWOTTE                , Position.of(23,-3)   , false),
    BRIGANDIN_VILLAGE_ZOTHS       (BRIGANDIN   , VILLAGE_ZOTHS              , Position.of(-53,15)  , false),
    FRIGOSTIEN_CHAMPS_GLACE       (FRIGOSTIEN  , CHAMPS_GLACE               , Position.of(-68,-34) , false),
    FRIGOSTIEN_BERCEAU_ALMA       (FRIGOSTIEN  , BERCEAU_ALMA               , Position.of(-56,-74) , false),
    FRIGOSTIEN_LARMES_OURONIGRIDE (FRIGOSTIEN  , LARMES_OURONIGRIDE         , Position.of(-69,-87) , false),
    FRIGOSTIEN_CREVASSE_PERGE     (FRIGOSTIEN  , CREVASSE_PERGE             , Position.of(-78,-82) , false),
    FRIGOSTIEN_FORET_PETRIFIEE    (FRIGOSTIEN  , FORET_PETRIFIEE            , Position.of(-76,-66) , false),
    FRIGOSTIEN_CROCS_VERRE        (FRIGOSTIEN  , CROCS_VERRE                , Position.of(-66,-65) , false),
    FRIGOSTIEN_MONT_TORRIDEAU     (FRIGOSTIEN  , MONT_TORRIDEAU             , Position.of(-63,-72) , false),
    FRIGOSTIEN_PLAINE_SAKAI       (FRIGOSTIEN  , PLAINE_SAKAI               , Position.of(-55,-41) , false),
    SKIS_SOMBRES                  (SKIS        , EXCAVATION_MR              , Position.of(-76, -46), false),
    SKIS_GLISSANTS                (SKIS        , SERRE_RM                   , Position.of(-84, -49), false),
    SKIS_SOUPLES                  (SKIS        , BOURGADE                   , Position.of(-64, -55), false),
    SKIS_RUSTIQUES                (SKIS        , SubArea.FRIGOSTIEN         , Position.of(-68,-34) , false),
    SCAEROPLANE_PLAINES_HERBEUSES (SCAEROPLANE , PLAINES_HERBEUSES          , Position.of(-56,22)  , false),
    SCAEROPLANE_VILLAGE_ELEVEURS  (SCAEROPLANE , VILLAGE_ELEVEURS_OTOMAI    , Position.of(-57,4)   , false),
    SCAEROPLANE_VILLAGE_COTIER    (SCAEROPLANE , VILLAGE_COTIER             , Position.of(-49,14)  , false),
    SCAEROPLANE_ARBRE_HAKAM       (SCAEROPLANE , VILLAGE_CANOPEE            , Position.of(-54,19)  , false),
    CHAR_PORT_SARAKECH            (CHAR_A_VOILE, PORT_SARAKECH              , Position.of(16, -57) , false),
    CHAR_TERRITOIRE_CACTERRE      (CHAR_A_VOILE, TERRITOIRE_CACTERRE        , Position.of(12, -63) , false),
    CHAR_GORGE_VENTS_HURLANTS     (CHAR_A_VOILE, GORGE_VENTS_HURLANTS       , Position.of(11, -70) , false);

    private TransportType type;
    private SubArea subArea;
    private Position position;
    private boolean isAvailableUnderConditions;
}