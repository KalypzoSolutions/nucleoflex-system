package it.einjojo.nucleoflex.player;

import java.util.UUID;

/**
 * This interface provides a method for looking up the unique ID (UUID) of a player given their name.
 * It is used in the PlayerNameCache class to retrieve the UUID for a player name that is not currently in the cache.
 */
public interface PlayerNameLookup {

    /**
     * Looks up the UUID for a given player name.
     * @param name The name of the player for which to look up the UUID.
     * @return The UUID of the player, or null if the player does not exist.
     */
    UUID lookupUniqueId(String name);
}