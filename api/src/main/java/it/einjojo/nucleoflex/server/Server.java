package it.einjojo.nucleoflex.server;

import it.einjojo.nucleoflex.player.NFPlayer;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Server interface
 * Represents a server in the network
 */
public interface Server {
    /**
     * @return the name of the server
     */
    String name();

    /**
     * @return amount of players connected to the server
     */
    int playerCount();

    /**
     * @return the maximum amount of players that can be connected to the server
     */
    int maxPlayers();

    /**
     * @return the players connected to the server
     */
    Collection<NFPlayer> players();

    /**
     * @return the players connected to the server asynchronously
     */
    CompletableFuture<Collection<NFPlayer>> playersAsync();

    /**
     * Connects a player to the server
     * @param playerUniqueId the UUID of the player to connect
     */
    void connect(UUID playerUniqueId);

}
