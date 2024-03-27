package it.einjojo.nucleoflex.player.handler;

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
    private static final String POSITION_ID = "pos"; // ID for position requests
    private static final String TELEPORT_ID = "tp"; // ID for teleport requests
    private final RequestService requestService;
    private final AbstractPlayerConnectionHandler playerConnectionHandler;
    private final InternalLogger logger;

    protected AbstractPositionHandler(RequestService requestService, AbstractPlayerConnectionHandler playerConnectionHandler, LogManager logManager) {
        this.requestService = requestService;
        this.playerConnectionHandler = playerConnectionHandler;
        this.logger = logManager.logger(getClass());
    }

    @Override
    public void processMessage(ChannelMessage message) {
        if (message.messageTypeID().equals(POSITION_ID)) {
            String payload = message.content();
            ByteArrayDataInput input = ByteStreams.newDataInput(payload.getBytes());

        }
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
                .messageTypeID(POSITION_ID)
                .content(payload.toByteArray())
                .recipient(ChannelReceiver.server(playerServiceName)).build();
        requestService.publishRequest(msg).whenComplete(((message, throwable) -> {
            if (throwable != null) {
                future.completeExceptionally(throwable);
                return;
            }
        }));
        return future;
    }

    public void teleport(UUID player, Position target) {
        switch (target.type()) {
            case SERVER:

                break;
            case GROUP:

                break;
            case UNSPECIFIED:

            default:
                throw new IllegalStateException("Unexpected value: " + target.type());
        }
    }
}
