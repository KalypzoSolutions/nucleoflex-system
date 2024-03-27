package it.einjojo.nucleoflex.player;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * This class is responsible for caching player names and their corresponding UUIDs.
 * It uses a Caffeine cache to store the data and provides a method to retrieve the UUID for a given player name.
 */
public class PlayerNameCache {

    private final PlayerNameLookup lookup;
    private final Cache<String, UUID> nameCache = Caffeine.newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build();

    /**
     * Constructor for the PlayerNameCache class.
     *
     * @param lookup The PlayerNameLookup instance used to look up UUIDs for player names.
     */
    public PlayerNameCache(PlayerNameLookup lookup) {
        this.lookup = lookup;
    }

    /**
     * Retrieves the UUID for a given player name from the cache.
     * If the UUID is not in the cache, it uses the PlayerNameLookup instance to look it up and stores it in the cache.
     *
     * @param name The player name for which to retrieve the UUID.
     * @return An Optional containing the UUID if it was found, or an empty Optional if it was not.
     */
    public Optional<UUID> getUniqueId(String name) {
        return Optional.ofNullable(nameCache.get(name, lookup::lookupUniqueId));
    }

    /**
     * Asynchronously retrieves the UUID for a given player name.
     *
     * @param name The player name for which to retrieve the UUID.
     * @return A CompletableFuture containing an Optional with the UUID if it was found, or an empty Optional if it was not.
     */
    public CompletableFuture<Optional<UUID>> getUniqueIdAsync(String name) {
        return CompletableFuture.supplyAsync(() -> getUniqueId(name));
    }

}