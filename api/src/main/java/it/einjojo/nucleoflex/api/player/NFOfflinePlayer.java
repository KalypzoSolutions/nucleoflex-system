package it.einjojo.nucleoflex.api.player;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * NFOfflinePlayer
 * Represents a player that is offline.
 */
public interface NFOfflinePlayer {

    /**
     * @return the UUID of the player.
     */
    UUID uuid();

    /**
     * @return the name of the player.
     */
    String name();

    /**
     * @return the first join timestamp of the player in millis.
     */
    long firstJoin();

    /**
     * @return the last join timestamp of the player in millis.
     */
    long lastJoin();

    /**
     * @return gets the player as a NFPlayer if the player is online.
     */
    Optional<NFPlayer> toPlayer();

    /**
     * @return true if the player is online.
     */
    boolean isOnline();

    /**
     * @return the economy holder of the player.
     */
    PlayerBalance economy();

    /**
     * @return the economy holder of the player asynchronously.
     */
    CompletableFuture<PlayerBalance> economyAsync();

}
