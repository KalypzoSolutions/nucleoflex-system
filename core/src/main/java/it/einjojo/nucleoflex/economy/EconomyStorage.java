package it.einjojo.nucleoflex.economy;

import it.einjojo.nucleoflex.api.player.PlayerEconomy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface EconomyStorage {
    /**
     * Stores a new player economy in the database
     *
     * @param playerEconomy the player economy to store
     */
    void persistEconomy(@NotNull PlayerEconomy playerEconomy);

    /**
     * Updates the player economy in the database
     *
     * @param playerEconomy the player economy to update
     */
    void updateEconomy(@NotNull PlayerEconomy playerEconomy);

    /**
     * Loads the player economy from the database
     *
     * @param player the player to load the economy
     * @return the player economy
     */
    @Nullable PlayerEconomy loadEconomy(UUID player);

    /**
     * Deletes the player economy from the database
     *
     * @param player the player to delete the economy
     */
    void deleteEconomy(UUID player);

    /**
     * Checks if the player economy exists in the database
     *
     * @param uuid the player to check
     * @return true if the player economy exists
     */
    boolean existsEconomy(UUID uuid);

}
