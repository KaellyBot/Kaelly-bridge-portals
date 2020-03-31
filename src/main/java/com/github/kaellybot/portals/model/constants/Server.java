package com.github.kaellybot.portals.model.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Server {

    AGRIDE        ("server.agride"       ),
    ATCHAM        ("server.atcham"       ),
    CROCABULIA    ("server.crocabulia"   ),
    ECHO          ("server.echo"         ),
    MERIANA       ("server.meriana"      ),
    OMBRE         ("server.ombre"        ),
    OTO_MUSTAM    ("server.oto_mustam"   ),
    RUBILAX       ("server.rubilax"      ),
    PANDORE       ("server.pandore"      ),
    USH           ("server.ush"          ),
    JULITH        ("server.julith"       ),
    NIDAS         ("server.nidas"        ),
    MERKATOR      ("server.merkator"     ),
    FURYE         ("server.furye"        ),
    BRUMEN        ("server.brumen"       ),
    ILYZAELLE     ("server.ilyzaelle"    ),
    JAHASH        ("server.jahash"       ),
    THANATENA     ("server.thanatena"    ),
    TEMPORIS_I    ("server.temporis_i"   ),
    TEMPORIS_II   ("server.temporis_ii"  ),
    TEMPORIS_III  ("server.temporis_iii" ),
    TEMPORIS_IV   ("server.temporis_iv"  ),
    TEMPORIS_V    ("server.temporis_v"   ),
    TEMPORIS_VI   ("server.temporis_vi"  ),
    TEMPORIS_VII  ("server.temporis_vii" ),
    TEMPORIS_VIII ("server.temporis_viii"),
    TEMPORIS_IX   ("server.temporis_ix"  ),
    TEMPORIS_X    ("server.temporis_x"   );

    private String key;
}