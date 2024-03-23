package it.einjojo.nucleoflex.player;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.base.Preconditions;
import it.einjojo.nucleoflex.api.player.NFOfflinePlayer;
import it.einjojo.nucleoflex.api.player.NFPlayer;
import it.einjojo.nucleoflex.api.player.PlayerManager;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * Uses playerNameCache
 */
public abstract class AbstractPlayerManager implements PlayerManager {

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

    abstract CompletableFuture<NFPlayer> loadPlayer(UUID uniqueId, Executor executor);

    abstract CompletableFuture<NFOfflinePlayer> loadOfflinePlayer(UUID uniqueId, Executor executor);

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

    // NAME CACHE USAGE SHORTCUTS
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
    // NAME CACHE USAGE SHORTCUTS END


}
