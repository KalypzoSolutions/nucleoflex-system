package it.einjojo.nucleoflex.api.player;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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
     * @param playerName the name of the player
     * @return true if the player is in the container
     */
    boolean containsPlayer(String playerName);

    /**
     * @param playerUUID the UUID of the player
     * @return the player if the player is in the container
     */
    Optional<NFPlayer> player(UUID playerUUID);

    /**
     * @param playerName the name of the player
     * @return the player if the player is in the container
     */
    Optional<NFPlayer> playerByName(String playerName);

    /**
     * @param playerUUID the UUID of the player
     * @return the player if the player is in the container asynchronously
     */
    CompletableFuture<Optional<NFPlayer>> playerAsync(UUID playerUUID);

    /**
     * @param playerName the name of the player
     * @return the player if the player is in the container asynchronously
     */
    CompletableFuture<Optional<NFPlayer>> playerByNameAsync(String playerName);

    /**
     * @return a collection of all players in the container
     */
    Collection<NFPlayer> players();

    /**
     * @return a collection of all players in the container asynchronously
     */
    CompletableFuture<Collection<NFPlayer>> playersAsync();
}
