package it.einjojo.nucleoflex.api.impl;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import it.einjojo.nucleoflex.api.player.NFOfflinePlayer;
import it.einjojo.nucleoflex.api.player.NFPlayer;
import it.einjojo.nucleoflex.api.player.PlayerContainerManager;
import it.einjojo.nucleoflex.api.player.PlayerManager;
import it.einjojo.nucleoflex.player.PlayerNameCache;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


/**
 * Abstract class for managing players, both online and offline.
 * This class provides the basic functionality for loading and caching player data.
 */
public abstract class AbstractPlayerManager implements PlayerManager, PlayerContainerManager {

    protected final PlayerNameCache playerNameCache;
    protected final AsyncLoadingCache<UUID, NFPlayer> onlinePlayerCache = Caffeine.newBuilder()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .buildAsync(this::loadPlayer);
    protected final AsyncLoadingCache<UUID, NFOfflinePlayer> offlinePlayerCache = Caffeine.newBuilder()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .buildAsync(this::loadOfflinePlayer);


    protected AbstractPlayerManager(PlayerNameCache playerNameCache) {
        this.playerNameCache = playerNameCache;
    }

    /**
     * Abstract method for loading a player.
     *
     * @param uniqueId The unique ID of the player.
     * @param executor The executor to run the loading task provided by caffeine cache.
     * @return A CompletableFuture that completes with the loaded player.
     */
    abstract CompletableFuture<NFPlayer> loadPlayer(UUID uniqueId, Executor executor);

    /**
     * Abstract method for loading an offline player.
     *
     * @param uniqueId The unique ID of the player.
     * @param executor The executor to run the loading task provided by caffeine cache.
     * @return A CompletableFuture that completes with the loaded offline player.
     */
    abstract CompletableFuture<NFOfflinePlayer> loadOfflinePlayer(UUID uniqueId, Executor executor);


    // USING CACHE
    @Override
    public Optional<NFPlayer> player(UUID uniqueId) {
        if (uniqueId == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(onlinePlayerCache.synchronous().get(uniqueId));
    }

    @Override
    public CompletableFuture<Optional<NFPlayer>> playerAsync(UUID uniqueId) {
        if (uniqueId == null) {
            return CompletableFuture.completedFuture(Optional.empty());
        }
        return onlinePlayerCache.get(uniqueId).thenApply(Optional::ofNullable);
    }

    @Override
    public Optional<NFOfflinePlayer> offlinePlayer(UUID uniqueId) {
        if (uniqueId == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(offlinePlayerCache.synchronous().get(uniqueId));
    }

    @Override
    public CompletableFuture<Optional<NFOfflinePlayer>> offlinePlayerAsync(UUID uniqueId) {
        if (uniqueId == null) {
            return CompletableFuture.completedFuture(Optional.empty());
        }
        return offlinePlayerCache.get(uniqueId).thenApply(Optional::ofNullable);
    }


    // USING THE PLAYER NAME CACHE TO GET THE UUID AND THEN GET THE PLAYER
    @Override
    public Optional<NFPlayer> playerByName(String name) {
        var optionalUUID = playerNameCache.getUniqueId(name);
        if (optionalUUID.isEmpty()) {
            return Optional.empty();
        }
        return player(optionalUUID.get());
    }

    @Override
    public CompletableFuture<Optional<NFPlayer>> playerByNameAsync(String name) {
        var optionalUUID = playerNameCache.getUniqueId(name);
        if (optionalUUID.isEmpty()) {
            return CompletableFuture.completedFuture(Optional.empty());
        }
        return playerAsync(optionalUUID.get());
    }

    @Override
    public Optional<NFOfflinePlayer> offlinePlayerByName(String name) {
        var optionalUUID = playerNameCache.getUniqueId(name);
        if (optionalUUID.isEmpty()) {
            return Optional.empty();
        }
        return offlinePlayer(optionalUUID.get());
    }

    @Override
    public CompletableFuture<Optional<NFOfflinePlayer>> offlinePlayerByNameAsync(String name) {
        var optionalUUID = playerNameCache.getUniqueId(name);
        if (optionalUUID.isEmpty()) {
            return CompletableFuture.completedFuture(Optional.empty());
        }
        return offlinePlayerAsync(optionalUUID.get());
    }


}
