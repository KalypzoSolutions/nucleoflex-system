package it.einjojo.nucleoflex.player;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PlayerNameCache {

    private final PlayerNameLookup lookup;
    private final Cache<String, UUID> nameCache = Caffeine.newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build();

    public PlayerNameCache(PlayerNameLookup lookup) {
        this.lookup = lookup;
    }


    public Optional<UUID> getUniqueId(String name) {
        return Optional.ofNullable(nameCache.get(name, lookup::lookupUniqueId));
    }

}
