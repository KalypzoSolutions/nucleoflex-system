package it.einjojo.nucleoflex.player;

import java.util.UUID;

public interface PlayerNameLookup {
    UUID lookupUniqueId(String name);
}
