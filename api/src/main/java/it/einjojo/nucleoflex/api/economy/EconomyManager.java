package it.einjojo.nucleoflex.api.economy;

import it.einjojo.nucleoflex.api.player.PlayerEconomy;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface EconomyManager {
    PlayerEconomy playerEconomy(UUID player);

    CompletableFuture<PlayerEconomy> playerEconomyAsync(UUID player);

    void update(PlayerEconomy playerEconomy);

    void updateAsync(PlayerEconomy playerEconomy);

}
