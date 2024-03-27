package it.einjojo.nucleoflex.economy;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import it.einjojo.nucleoflex.api.broker.BrokerService;
import it.einjojo.nucleoflex.api.broker.ChannelMessage;
import it.einjojo.nucleoflex.api.broker.MessageProcessor;
import it.einjojo.nucleoflex.api.economy.EconomyManager;
import it.einjojo.nucleoflex.api.player.PlayerEconomy;
import it.einjojo.nucleoflex.economy.PlayerEconomyObserver;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;


public abstract class AbstractEconomyManager implements EconomyManager {

    private AsyncLoadingCache<UUID, PlayerEconomy> loadedEconomies = Caffeine.newBuilder()
            .expireAfterAccess(2, TimeUnit.MINUTES)
            .buildAsync(this::loadEconomyAsync);


    public abstract CompletableFuture<PlayerEconomy> loadEconomyAsync(UUID player, Executor executor);


    @Override
    public PlayerEconomy playerEconomy(UUID player) {
        return loadedEconomies.synchronous().get(player);
    }

    @Override
    public CompletableFuture<PlayerEconomy> playerEconomyAsync(UUID player) {
        return loadedEconomies.get(player);
    }


}
