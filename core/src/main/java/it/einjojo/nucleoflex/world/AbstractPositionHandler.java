package it.einjojo.nucleoflex.world;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import it.einjojo.nucleoflex.api.broker.ChannelMessage;
import it.einjojo.nucleoflex.api.broker.ChannelReceiver;
import it.einjojo.nucleoflex.api.broker.MessageProcessor;
import it.einjojo.nucleoflex.api.broker.RequestService;
import it.einjojo.nucleoflex.api.log.InternalLogger;
import it.einjojo.nucleoflex.api.log.LogManager;
import it.einjojo.nucleoflex.api.world.Position;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractPositionHandler implements MessageProcessor {
    private static final String MSG_ID = "pos";
    private final RequestService requestService;
    private final InternalLogger logger;

    protected AbstractPositionHandler(RequestService requestService, LogManager logManager) {
        this.requestService = requestService;
        this.logger = logManager.logger(getClass());
    }

    @Override
    public void processMessage(ChannelMessage message) {
        if (!message.messageTypeID().equals(MSG_ID)) return;
        String payload = message.content();
        ByteArrayDataInput input = ByteStreams.newDataInput(payload.getBytes());

    }

    /**
     * Request the position of a player
     *
     * @param uuid The UUID of the player
     * @return The position of the player or null if the player is not on the server
     */
    abstract Position positionOfPlayer(UUID uuid);

    public CompletableFuture<Position> requestPositionOfPlayer(UUID playerUUID, String playerServiceName) {
        Preconditions.checkNotNull(playerUUID, "playerUUID can not be null");
        Preconditions.checkNotNull(playerServiceName, "playerServiceName can not be null");
        CompletableFuture<Position> future = new CompletableFuture<>();
        ByteArrayDataOutput payload = ByteStreams.newDataOutput();
        payload.writeUTF(playerUUID.toString());
        ChannelMessage msg = ChannelMessage.builder()
                .messageTypeID(MSG_ID)
                .content(payload.toByteArray())
                .recipient(ChannelReceiver.service(playerServiceName)).build();
        requestService.publishRequest(msg).whenComplete(((message, throwable) -> {
            if (throwable != null) {
                future.completeExceptionally(throwable);
                return;
            }

        }));

        return future;
    }


}
