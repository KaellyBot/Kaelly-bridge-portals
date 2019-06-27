package com.github.kaellybot.portals.model.constants;

import com.github.kaellybot.portals.model.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.github.kaellybot.portals.model.constants.TransportArea.*;
import static com.github.kaellybot.portals.model.constants.TransportType.*;

@Getter
@AllArgsConstructor
public enum Transport {

    // Zaaps
    BORD_DE_LA_FORET_MALEFIQUE(TRANSPORT_TYPE_ZAAP, TRANSPORT_AREA_AMAKNA, "transport.souszone.bord_foret_malefique", Position.of(-1, 13), true),
    CHATEAU_D_AMAKNA(TRANSPORT_TYPE_ZAAP, TRANSPORT_AREA_AMAKNA, "transport.souszone.chateau_amakna", Position.of(3,-5), true),
    COIN_DES_BOUFTOUS(TRANSPORT_TYPE_ZAAP, TRANSPORT_AREA_AMAKNA, "transport.souszone.coin_bouftous", Position.of(5,7), true),
    MONTAGNE_DES_CRAQUELEURS(TRANSPORT_TYPE_ZAAP, TRANSPORT_AREA_AMAKNA, "transport.souszone.montagne_craqueleurs", Position.of(-5, -8), true),
    PLAINE_DES_SCARAFEUILLES(TRANSPORT_TYPE_ZAAP, TRANSPORT_AREA_AMAKNA, "transport.souszone.plaine_scarafeuilles", Position.of(-1,24), true),
    PORT_DE_MADRESTAM(TRANSPORT_TYPE_ZAAP, TRANSPORT_AREA_AMAKNA, "transport.souszone.port_madrestam", Position.of(7,-4), true),
    VILLAGE_D_AMAKNA(TRANSPORT_TYPE_ZAAP, TRANSPORT_AREA_AMAKNA, "transport.souszone.village_amakna", Position.of(-2,0), true),
    CITE_D_ASTRUB(TRANSPORT_TYPE_ZAAP, TRANSPORT_ASTRUB, "transport.souszone.cite_astrub", Position.of(5,-18), true),
    SUFOKIA(TRANSPORT_TYPE_ZAAP, TRANSPORT_BAIE_SUFOKIA, "transport.souszone.sufokia", Position.of(13, 26), true),
    TEMPLE_DES_ALLIANCES(TRANSPORT_TYPE_ZAAP, TRANSPORT_BAIE_SUFOKIA, "transport.souszone.temple_alliances", Position.of(13, 35), true),
    RIVAGE_SUFOKIEN(TRANSPORT_TYPE_ZAAP, TRANSPORT_BAIE_SUFOKIA, "transport.souszone.rivage_sufokien", Position.of(10,22), true),
    BONTA(TRANSPORT_TYPE_ZAAP, TRANSPORT_BONTA, "transport.souszone.centre_ville", Position.of(-32,-56), true),
    VILLAGE_COTIER(TRANSPORT_TYPE_ZAAP, TRANSPORT_ILE_OTOMAI, "transport.souszone.village_cotier", Position.of(-46,18), true),
    VILLAGE_DE_LA_CANOPEE(TRANSPORT_TYPE_ZAAP, TRANSPORT_ILE_OTOMAI, "transport.souszone.village_canopee", Position.of(-54,16), true),
    LA_BOURGADE(TRANSPORT_TYPE_ZAAP, TRANSPORT_ILE_FRIGOST, "transport.souszone.bourgade", Position.of(-78,-41), true),
    VILLAGE_ENSEVELI(TRANSPORT_TYPE_ZAAP, TRANSPORT_ILE_FRIGOST, "transport.souszone.village_enseveli", Position.of(-77,-73), true),
    PLAGE_DE_LA_TORTUE(TRANSPORT_TYPE_ZAAP, TRANSPORT_ILE_MOON, "transport.souszone.plage_tortue", Position.of(35,12), true),
    ILE_DE_LA_CAWOTTE(TRANSPORT_TYPE_ZAAP, TRANSPORT_ILE_WABBIT, "transport.souszone.ile_cawotte", Position.of(25,-4), true),
    LABORATOIRES_ABANDONNES(TRANSPORT_TYPE_ZAAP, TRANSPORT_ILE_WABBIT, "transport.souszone.laboratoires_abandonnes", Position.of(27,-14), false),
    ROUTE_DES_ROULOTTES(TRANSPORT_TYPE_ZAAP, TRANSPORT_LANDES_SIDIMOTE, "transport.souszone.route_roulottes", Position.of(-25,12), true),
    TERRES_DESACREES(TRANSPORT_TYPE_ZAAP, TRANSPORT_LANDES_SIDIMOTE, "transport.souszone.terres_desacrees", Position.of(-15,25), true),
    VILLAGE_DES_ELEVEURS(TRANSPORT_TYPE_ZAAP, TRANSPORT_MONTAGNE_KOALAKS, "transport.souszone.village_eleveurs", Position.of(-16,1), true),
    VILLAGE_D_AERDALA(TRANSPORT_TYPE_ZAAP, TRANSPORT_PANDALA_AIR, "transport.souszone.village_aerdala", Position.of(17,-31), false),
    VILLAGE_D_AKWADALA(TRANSPORT_TYPE_ZAAP, TRANSPORT_PANDALA_EAU, "transport.souszone.village_akwadala", Position.of(23,-22), false),
    VILLAGE_DE_FEUDALA(TRANSPORT_TYPE_ZAAP, TRANSPORT_PANDALA_FEU, "transport.souszone.village_feudala", Position.of(29,-49), false),
    FAUBOURGS_DE_PANDALA(TRANSPORT_TYPE_ZAAP, TRANSPORT_PANDALA_NEUTRE, "transport.souszone.faubourgs_pandala", Position.of(26,-37), true),
    VILLAGE_DE_TERRDALA(TRANSPORT_TYPE_ZAAP, TRANSPORT_PANDALA_TERRE, "transport.souszone.village_terrdala", Position.of(30,-38), false),
    CHAMPS_DE_CANIA(TRANSPORT_TYPE_ZAAP, TRANSPORT_PLAINES_CANIA, "transport.souszone.champs_cania", Position.of(-27,-36), true),
    LAC_DE_CANIA(TRANSPORT_TYPE_ZAAP, TRANSPORT_PLAINES_CANIA, "transport.souszone.lac_cania", Position.of(-3,-42), true),
    MASSIF_DE_CANIA(TRANSPORT_TYPE_ZAAP, TRANSPORT_PLAINES_CANIA, "transport.souszone.massif_cania", Position.of(-13,-28), true),
    PLAINE_DES_PORKASS(TRANSPORT_TYPE_ZAAP, TRANSPORT_PLAINES_CANIA, "transport.souszone.plaine_porkass", Position.of(-5,-23), true),
    PLAINES_ROCHEUSES(TRANSPORT_TYPE_ZAAP, TRANSPORT_PLAINES_CANIA, "transport.souszone.plaines_rocheuses", Position.of(-17,-47), true),
    ROUTES_ROCAILLEUSES(TRANSPORT_TYPE_ZAAP, TRANSPORT_PLAINES_CANIA, "transport.souszone.routes_rocailleuses", Position.of(-20,-20), true),
    VILLAGE_DES_KANIGS(TRANSPORT_TYPE_ZAAP, TRANSPORT_PLAINES_CANIA, "transport.souszone.village_kanigs", Position.of(0,-56), false),
    DUNE_DES_OSSEMENTS(TRANSPORT_TYPE_ZAAP, TRANSPORT_SAHARACH, "transport.souszone.dune_ossements", Position.of(15,-58), true),
    BERCEAU(TRANSPORT_TYPE_ZAAP, TRANSPORT_TAINELA, "transport.souszone.berceau", Position.of(1,-32), true),
    VILLAGE_DES_DOPEULS(TRANSPORT_TYPE_ZAAP, TRANSPORT_TERRITOIRE_DOPEULS, "transport.souszone.village_dopeuls", Position.of(-34,-8), false),
    VILLAGE_DES_BRIGANDINS(TRANSPORT_TYPE_ZAAP, TRANSPORT_VILLAGE_BRIGANDINS, "transport.souszone.village_brigandins", Position.of(-16,-24), false),
    VILLAGE_DES_ZOTHS(TRANSPORT_TYPE_ZAAP, TRANSPORT_VILLAGE_ZOTHS, "transport.souszone.village_zoths", Position.of(-53,18), false),
    BRAKMAR(TRANSPORT_TYPE_ZAAP, TRANSPORT_BRAKMAR, "transport.souszone.centre_ville", Position.of(-26,35), true),

    //Foreuses
    FOREUSE_LABORATOIRE_ABANDONNE(TRANSPORT_TYPE_FOREUSE, TODO, "", Position.of(27,-14), true),
    FOREUSE_PANDALA_NEUTRE(TRANSPORT_TYPE_FOREUSE, TODO, "", Position.of(28,-30), true),
    FOREUSE_PLAGE_MOON(TRANSPORT_TYPE_FOREUSE, TODO, "", Position.of(36,4), true),
    FOREUSE_FEUILLAGE_ARBRE_HAKAM(TRANSPORT_TYPE_FOREUSE, TODO, "", Position.of(-53,17), true),
    FOREUSE_MINE_MAKSAGE(TRANSPORT_TYPE_FOREUSE, TODO, "La Forêt des Pins Perdus", Position.of(-55,-64), true),
    FOREUSE_MILIFUTAIE(TRANSPORT_TYPE_FOREUSE, TODO, "", Position.of(2,4), true),
    FOREUSE_KARTONPATH(TRANSPORT_TYPE_FOREUSE, TODO, "", Position.of(18, 11), true),
    FOREUSE_TERRITOIRE_PORCOS(TRANSPORT_TYPE_FOREUSE, TODO, "", Position.of(0,33), true),
    FOREUSE_BAIE_CANIA(TRANSPORT_TYPE_FOREUSE, TODO, "", Position.of(-29,-12), true),
    FOREUSE_GROTTE_HURIE(TRANSPORT_TYPE_FOREUSE, TODO, "Vallée Morh kitu", Position.of(-18,14), true),
    FOREUSE_MINE_HERALE(TRANSPORT_TYPE_FOREUSE, TODO, "Forêt amakna", Position.of(5,19), true),
    FOREUSE_MINE_ISTAIRAMEUR(TRANSPORT_TYPE_FOREUSE, TODO, "Montagne Basse des Craqueleurs", Position.of(-3,9), true),
    FOREUSE_MINE_HIPOUCE(TRANSPORT_TYPE_FOREUSE, TODO, "lande sidimote", Position.of(-23,25), true),
    FOREUSE_DENTS_PIERRE(TRANSPORT_TYPE_FOREUSE, TODO, "", Position.of(-4,-53), true),

    // Diligence d'atrub
    DILIGENCE_ASTRUB(TRANSPORT_TYPE_DILIGENCE, TODO, "", Position.of(0, -18), false),
    DILIGENCE_BAIE_CANIA(TRANSPORT_TYPE_DILIGENCE, TODO, "", Position.of(-29,-12), false),
    DILIGENCE_DOMAINE_FUNGUS(TRANSPORT_TYPE_DILIGENCE, TODO, "", Position.of(-12,29), false),
    DILIGENCE_CIMETIERE_HEROS(TRANSPORT_TYPE_DILIGENCE, TODO, "", Position.of(-21,-58), false),

    // Transport Brigandin
    BRIGANDIN_RIVIERE_KAWAII(TRANSPORT_TYPE_BRIGANDIN,TODO, "",Position.of(6,-1), true),
    BRIGANDIN_PORTE_BONTA(TRANSPORT_TYPE_BRIGANDIN,TODO, "",Position.of(-31,-48), true),
    BRIGANDIN_PORTE_BRAKMAR(TRANSPORT_TYPE_BRIGANDIN,TODO, "",Position.of(-26,29), true),
    BRIGANDIN_VILLAGE_BWORKS(TRANSPORT_TYPE_BRIGANDIN,TODO, "",Position.of(-1,8), true),
    BRIGANDIN_PRESQU_ILE_DRAGOEUFS(TRANSPORT_TYPE_BRIGANDIN,TODO, "",Position.of(-3,27), true),
    BRIGANDIN_VILLAGE_BRIGANDINS(TRANSPORT_TYPE_BRIGANDIN,TODO, "",Position.of(-17,-26), true),
    BRIGANDIN_PENINSULE_GELEES(TRANSPORT_TYPE_BRIGANDIN,TODO, "",Position.of(11,30), true),
    BRIGANDIN_PANDALA(TRANSPORT_TYPE_BRIGANDIN,TODO, "",Position.of(18,-39), true),
    BRIGANDIN_ILE_MINOTOROR(TRANSPORT_TYPE_BRIGANDIN,TODO, "",Position.of(-41,-18), true),
    BRIGANDIN_ILE_MOON(TRANSPORT_TYPE_BRIGANDIN,TODO, "",Position.of(35,4), true),
    BRIGANDIN_ILE_WABBITS(TRANSPORT_TYPE_BRIGANDIN,TODO, "",Position.of(23,-3), true),
    BRIGANDIN_VILLAGE_ZOTHS(TRANSPORT_TYPE_BRIGANDIN,TODO, "",Position.of(-53,15), true),

    // Transport Frigostien
    TRANSPORTEUR_FRIGOSTIEN_CHAMPS_GLACE(TRANSPORT_TYPE_TRANSPORTEUR_FRIGOSTIEN, TODO, "", Position.of(-68,-34), false),
    TRANSPORTEUR_FRIGOSTIEN_BERCEAU_ALMA(TRANSPORT_TYPE_TRANSPORTEUR_FRIGOSTIEN, TODO, "", Position.of(-56,-74), false),
    TRANSPORTEUR_FRIGOSTIEN_LARMES_OURONIGRIDE(TRANSPORT_TYPE_TRANSPORTEUR_FRIGOSTIEN, TODO, "", Position.of(-69,-87), false),
    TRANSPORTEUR_FRIGOSTIEN_CREVASSE_PERGE(TRANSPORT_TYPE_TRANSPORTEUR_FRIGOSTIEN, TODO, "", Position.of(-78,-82), false),
    TRANSPORTEUR_FRIGOSTIEN_FORET_PETRIFIEE(TRANSPORT_TYPE_TRANSPORTEUR_FRIGOSTIEN, TODO, "", Position.of(-76,-66), false),
    TRANSPORTEUR_FRIGOSTIEN_CROCS_VERRE(TRANSPORT_TYPE_TRANSPORTEUR_FRIGOSTIEN, TODO, "", Position.of(-66,-65), false),
    TRANSPORTEUR_FRIGOSTIEN_MONT_TORRIDEAU(TRANSPORT_TYPE_TRANSPORTEUR_FRIGOSTIEN, TODO, "", Position.of(-63,-72), false),
    TRANSPORTEUR_FRIGOSTIEN_PLAINE_SAKAI(TRANSPORT_TYPE_TRANSPORTEUR_FRIGOSTIEN, TODO, "", Position.of(-55,-41), false),

    // Ski Frigost
    SKI_BAMBOU(TRANSPORT_TYPE_SKI, TODO, "Entrée de la Bourgade", Position.of(-76, -46), false),
    SKI_TREMBLE(TRANSPORT_TYPE_SKI, TODO, "Serre du Royalmouth", Position.of(-84, -49), false),
    SKI_ORME(TRANSPORT_TYPE_SKI, TODO, "Excavation du Mansot Royal", Position.of(-64, -55), false),
    SKI_CHENE(TRANSPORT_TYPE_SKI, TODO, "Au transporteur frigostien", Position.of(-68,-34), false),

    // Scaéroplane
    SCAEROPLANE_PLAINES_HERBEUSES(TRANSPORT_TYPE_SCAEROPLANE,TODO, "",Position.of(-56,22), false),
    SCAEROPLANE_VILLAGE_ELEVEURS(TRANSPORT_TYPE_SCAEROPLANE,TODO, "",Position.of(-57,4), false),
    SCAEROPLANE_VILLAGE_COTIER(TRANSPORT_TYPE_SCAEROPLANE,TODO, "",Position.of(-49,14), false),
    SCAEROPLANE_ARBRE_HAKAM(TRANSPORT_TYPE_SCAEROPLANE,TODO, "",Position.of(-54,19), false);

    //Zaapis (Bonta, Brâkmar, Sufokia, Marches magmatiques, Saharach)
    // TODO

    private TransportType type;
    private TransportArea area;
    private String subArea;
    private Position position;
    private boolean isAvailableUnderConditions;
}