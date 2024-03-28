package it.einjojo.nucleoflex.api.economy;

import it.einjojo.nucleoflex.api.player.PlayerEconomy;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface EconomyManager {

    PlayerEconomy playerEconomy(UUID player);

    /**
     * Get the player economy async
     *
     * @param player the player to get the economy
     * @return the player economy
     */
    CompletableFuture<PlayerEconomy> playerEconomyAsync(UUID player);

    /**
     * Update the player economy in the database and publishes changes to other servers.
     *
     * @param playerEconomy the player economy to update
     */
    void update(PlayerEconomy playerEconomy);


    /**
     * Update the player economy async in the database.
     *
     * @param playerEconomy the player economy to update
     */
    void updateAsync(PlayerEconomy playerEconomy);

    /**
     * Publish the changes of the player economy to the broker.
     *
     * @param playerEconomy the player economy to publish
     */
    void publishChange(PlayerEconomy playerEconomy);

    /**
     * Unload the player economy from every the cache.
     * @param player the player to unload
     */
    void unload(UUID player);

}
