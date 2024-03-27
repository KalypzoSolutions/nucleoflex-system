package it.einjojo.nucleoflex.api.player;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * NFOfflinePlayer
 * Represents a player that is offline.
 */
public interface NFOfflinePlayer extends PlayerDataSnapshot {


    /**
     * @return gets the player as a NFPlayer if the player is online.
     */
    Optional<NFPlayer> toPlayer();


    /**
     * @return the economy holder of the player.
     */
    PlayerEconomy economy();

    /**
     * @return the economy holder of the player asynchronously.
     */
    CompletableFuture<PlayerEconomy> economyAsync();

    /**
     * @param permission the permission to check.
     * @return true if the player has the given permission.
     */
    boolean hasPermission(String permission);


}
