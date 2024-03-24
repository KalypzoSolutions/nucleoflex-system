package it.einjojo.nucleoflex.api.player;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents a container for players.
 */
public interface PlayerContainer {
    /**
     * @return the amount of players in the container
     */
    int playerCount();

    /**
     * @param playerUUID the UUID of the player
     * @return true if the player is in the container
     */
    boolean containsPlayer(UUID playerUUID);

    /**
     * @param playerUUID the UUID of the player
     * @return the player if the player is in the container
     */
    Optional<NFPlayer> player(UUID playerUUID);


    /**
     * @return a collection of all players in the container
     */
    Collection<NFPlayer> players();

}
