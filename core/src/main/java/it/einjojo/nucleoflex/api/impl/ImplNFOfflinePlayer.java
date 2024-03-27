package it.einjojo.nucleoflex.api.impl;

import it.einjojo.nucleoflex.api.economy.EconomyManager;
import it.einjojo.nucleoflex.api.player.*;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * This class represents a player that is not currently online.
 */
public class ImplNFOfflinePlayer implements NFOfflinePlayer { //TODO Implement
    protected final PlayerManager playerManager;
    protected final EconomyManager economyManager;
    private final UUID uuid;
    private final String name;
    private final long firstJoin;
    protected long lastJoin;

    public ImplNFOfflinePlayer(PlayerDataSnapshot playerDataSnapshot, PlayerManager playerManager, EconomyManager economyManager) {
        this.uuid = playerDataSnapshot.uuid();
        this.name = playerDataSnapshot.name();
        this.firstJoin = playerDataSnapshot.firstJoin();
        this.lastJoin = playerDataSnapshot.lastJoin();
        this.playerManager = playerManager;
        this.economyManager = economyManager;
    }

    @Override
    public Optional<NFPlayer> toPlayer() {
        return playerManager.player(uuid());
    }

    @Override
    public PlayerEconomy economy() {
        return economyManager.playerEconomy(uuid());
    }

    @Override
    public CompletableFuture<PlayerEconomy> economyAsync() {
        return null;
    }

    @Override
    public boolean hasPermission(String permission) {
        return false;
    }

    @Override
    public UUID uuid() {
        return uuid;
    }

    @Override
    public String name() {
        return name;
    }

    public long firstJoin() {
        return firstJoin;
    }

    public long lastJoin() {
        return lastJoin;
    }

    @Override
    public boolean isOnline() {
        return false;
    }
}
