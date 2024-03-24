package it.einjojo.nucleoflex.api.impl;

import it.einjojo.nucleoflex.api.player.NFOfflinePlayer;
import it.einjojo.nucleoflex.api.player.NFPlayer;
import it.einjojo.nucleoflex.api.player.PlayerBalance;

import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ImplNFOfflinePlayer implements NFOfflinePlayer { //TODO Implement
    private final UUID uuid;
    private final String name;
    private final long firstJoin;
    protected long lastJoin;
    protected boolean online;

    protected transient WeakReference<PlayerBalance> balanceWeakReference;

    public ImplNFOfflinePlayer(UUID uuid, String name, long firstJoin, long lastJoin) {
        this.uuid = uuid;
        this.name = name;
        this.firstJoin = firstJoin;
        this.lastJoin = lastJoin;
    }

    //TODO
    @Override
    public Optional<NFPlayer> toPlayer() {
        return Optional.empty();
    }

    @Override
    public PlayerBalance economy() {
        return null;
    }

    @Override
    public CompletableFuture<PlayerBalance> economyAsync() {
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
        return online;
    }
}
