package it.einjojo.nucleoflex.api.impl;

import it.einjojo.nucleoflex.api.economy.EconomyManager;
import it.einjojo.nucleoflex.api.player.PlayerEconomy;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

//TODO
public class AbstractEconomyManager implements EconomyManager {

    @Override
    public PlayerEconomy playerEconomy(UUID player) {
        return null;
    }
    @Override
    public CompletableFuture<PlayerEconomy> playerEconomyAsync(UUID player) {
        return null;
    }

    @Override
    public void update(PlayerEconomy playerEconomy) {

    }

    @Override
    public void updateAsync(PlayerEconomy playerEconomy) {

    }
}
