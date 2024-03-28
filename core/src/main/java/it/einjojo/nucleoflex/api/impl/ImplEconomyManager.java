package it.einjojo.nucleoflex.api.impl;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.base.Preconditions;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import it.einjojo.nucleoflex.api.broker.*;
import it.einjojo.nucleoflex.api.economy.EconomyManager;
import it.einjojo.nucleoflex.api.log.InternalLogger;
import it.einjojo.nucleoflex.api.log.LogManager;
import it.einjojo.nucleoflex.api.player.PlayerEconomy;
import it.einjojo.nucleoflex.economy.EconomyStorage;
import it.einjojo.nucleoflex.economy.PlayerEconomyObserver;
import it.einjojo.nucleoflex.economy.TransactionLogger;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;


public class ImplEconomyManager implements EconomyManager, PlayerEconomyObserver, MessageProcessor {
    private static final String MESSAGE_ID = "balchange";
    private static final String CHANNEL_ID = "economy";
    private final BrokerService brokerService;
    private final EconomyStorage storage;
    private final InternalLogger logger;
    private final TransactionLogger transactionLogger;
    private final AsyncLoadingCache<UUID, PlayerEconomy> loadedEconomies = Caffeine.newBuilder()
            .buildAsync(this::loadEconomyAsync);

    public ImplEconomyManager(BrokerService brokerService, EconomyStorage storage, TransactionLogger transactionLogger) {
        Preconditions.checkNotNull(brokerService, "BrokerService cannot be null");
        Preconditions.checkNotNull(storage, "EconomyStorage cannot be null");
        Preconditions.checkNotNull(transactionLogger, "TransactionLogger cannot be null");
        this.brokerService = brokerService;
        this.storage = storage;
        this.transactionLogger = transactionLogger;
        logger = LogManager.getLogger("EconomyManager");
        brokerService.registerMessageProcessor(this);
        brokerService.subscribe(CHANNEL_ID);
    }

    @Override
    public String processingChannel() {
        return CHANNEL_ID;
    }

    public CompletableFuture<PlayerEconomy> loadEconomyAsync(UUID player, Executor executor) {
        return CompletableFuture.supplyAsync(() -> storage.loadEconomy(player), executor);
    }

    @Override
    public void update(PlayerEconomy playerEconomy) {
        try {
            storage.updateEconomy(playerEconomy);
            publishChange(playerEconomy); // Tell servers, who loaded economy before update reached database, about the change
        } catch (Exception ex) {
            logger.severe("Error while updating player economy: " + ex.getMessage());
        }
    }

    @Override
    public void updateAsync(PlayerEconomy playerEconomy) {
        CompletableFuture.runAsync(() -> {
            update(playerEconomy);
        });
    }

    @Override
    public PlayerEconomy playerEconomy(UUID player) {
        return loadedEconomies.synchronous().get(player);
    }

    @Override
    public CompletableFuture<PlayerEconomy> playerEconomyAsync(UUID player) {
        return loadedEconomies.get(player);
    }

    /**
     * Observer Method to publish a change in a player economy to all servers
     *
     * @param playerEconomy the player economy to publish
     */
    @Override
    public void publishChange(PlayerEconomy playerEconomy) {
        transactionLogger.log(playerEconomy.playerUuid(), ((ImplPlayerEconomy) playerEconomy).lastDelta());
        ByteArrayDataOutput payload = ByteStreams.newDataOutput();
        payload.writeUTF(playerEconomy.playerUuid().toString());
        payload.writeLong(playerEconomy.balance());
        ChannelMessage channelMessage = ChannelMessage.builder()
                .messageTypeID(MESSAGE_ID)
                .content(payload.toByteArray())
                .channel(CHANNEL_ID)
                .recipients(ChannelReceiver.all())
                .build();
        brokerService.publish(channelMessage);
    }

    @Override
    public void unload(UUID player) {
        loadedEconomies.synchronous().invalidate(player);
    }

    @Override
    public void processMessage(ChannelMessage message) {
        if (!message.messageTypeID().equals(MESSAGE_ID)) return;
        if (message.sender().equals(ChannelSender.self())) return; // Ignore own messages
        ByteArrayDataInput payload = ByteStreams.newDataInput(message.content().getBytes());
        UUID player = UUID.fromString(payload.readUTF());
        long balance = payload.readLong();
        CompletableFuture<PlayerEconomy> eco = loadedEconomies.getIfPresent(player);
        if (eco == null) return;
        eco.thenAccept(economy -> {
            ((ImplPlayerEconomy) economy).setBalanceWithoutPublish(balance);
            logger.info("Received balance change for player " + player + " new balance: " + balance);
        });
    }


}
