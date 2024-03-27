package it.einjojo.nucleoflex.api.impl;

import it.einjojo.nucleoflex.api.economy.EconomyManager;
import it.einjojo.nucleoflex.api.player.*;
import it.einjojo.nucleoflex.player.handler.PermissionHandler;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * This class represents a player that is not currently online.
 */
public class ImplNFOfflinePlayer implements NFOfflinePlayer { //TODO Implement

    protected transient final PlayerManager playerManager;
    protected transient final EconomyManager economyManager;
    protected transient final PermissionHandler permissionHandler;
    private final UUID uuid;
    private final String name;
    private final long firstJoin;
    protected long lastJoin;

    public ImplNFOfflinePlayer(PlayerDataSnapshot playerDataSnapshot, PlayerManager playerManager, EconomyManager economyManager, PermissionHandler permissionHandler) {
        this.uuid = playerDataSnapshot.uuid();
        this.name = playerDataSnapshot.name();
        this.firstJoin = playerDataSnapshot.firstJoin();
        this.lastJoin = playerDataSnapshot.lastJoin();
        this.playerManager = playerManager;
        this.economyManager = economyManager;
        this.permissionHandler = permissionHandler;
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
        return economyManager.playerEconomyAsync(uuid());
    }

    @Override
    public CompletableFuture<Boolean> hasPermissionAsync(String permission) {
        return permissionHandler.hasPermissionAsync(uuid(), permission);
    }

    @Override
    public boolean hasPermission(String permission) {
        return permissionHandler.hasPermission(uuid(), permission);
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
