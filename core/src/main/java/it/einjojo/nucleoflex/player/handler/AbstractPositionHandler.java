package it.einjojo.nucleoflex.player.handler;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import it.einjojo.nucleoflex.api.InternalAPI;
import it.einjojo.nucleoflex.api.broker.ChannelMessage;
import it.einjojo.nucleoflex.api.broker.ChannelReceiver;
import it.einjojo.nucleoflex.api.broker.MessageProcessor;
import it.einjojo.nucleoflex.api.log.InternalLogger;
import it.einjojo.nucleoflex.api.log.LogManager;
import it.einjojo.nucleoflex.api.player.NFPlayer;
import it.einjojo.nucleoflex.api.world.Position;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractPositionHandler implements MessageProcessor {
    private static final String POSITION_REQUEST = "pos"; // ID for position requests
    private static final String TELEPORT_MESSAGE = "tp"; // ID for teleport requests
    private final InternalAPI internal;
    private final Gson gson;
    private final PlayerConnectionHandler playerConnectionHandler;
    private final InternalLogger logger;

    protected AbstractPositionHandler(InternalAPI internal, Gson gson, PlayerConnectionHandler playerConnectionHandler) {
        this.internal = internal;
        this.gson = gson;
        this.playerConnectionHandler = playerConnectionHandler;
        this.logger = LogManager.getLogger(getClass());
    }

    @Override
    public void processMessage(ChannelMessage message) {
        if (message.messageTypeID().equals(POSITION_REQUEST)) { // A service has requested the position of a player.
            UUID playerUUID = UUID.fromString(message.content());
            Position position = positionOfPlayer(playerUUID);
            if (position == null) {
                logger.warning("Position of player (" + playerUUID + ") has been requested but player is not on the server");
                return;
            }
            ChannelMessage response = ChannelMessage.responseTo(message).content(gson.toJson(position)).build();
            internal.brokerService().publish(response);
        } else if (message.messageTypeID().equals(TELEPORT_MESSAGE)) { // A service has requested to teleport a player
            ByteArrayDataInput input = ByteStreams.newDataInput(message.content().getBytes());
            UUID playerUUID = UUID.fromString(input.readUTF());
            Position target = gson.fromJson(input.readUTF(), Position.class);
            setupTeleportLocally(playerUUID, target); // prepare the player to be teleported when they connect to the server
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
                .messageTypeID(POSITION_REQUEST)
                .content(payload.toByteArray())
                .recipient(ChannelReceiver.server(playerServiceName)).build();
        internal.requestService().publishRequest(msg).whenComplete(((message, throwable) -> {
            if (throwable != null) {
                future.completeExceptionally(throwable);
                return;
            }
            try {
                future.complete(gson.fromJson(message.content(), Position.class));
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        }));
        return future;
    }

    /**
     * Teleport a player to a target position with the given UUID.
     * When the given Player connects to the server the player will be teleported to the target position.
     * or if the player is already connected to the server the player will be teleported immediately.
     */
    abstract void setupTeleportLocally(UUID playerToBeTeleported, Position targetPosition);

    public void announceTeleport(UUID player, Position target) {

    }

    public void teleport(NFPlayer player, Position target) {
        switch (target.type()) {
            case SERVER:
                if (player.serverName().equals(internal.server().serverName())) {
                    setupTeleportLocally(player.uuid(), target);
                    return;
                }
                announceTeleport(player.uuid(), target);
                playerConnectionHandler.connectServer(player.uuid(), target.referenceName());
                break;
            case GROUP:
                if (player.server().groupName().equals(target.referenceName())) { // Is already on the group
                    setupTeleportLocally(player.uuid(), target);
                    return;
                }
                announceTeleport(player.uuid(), target);
                playerConnectionHandler.connectGroup(player.uuid(), target.referenceName());
                break;
            case UNSPECIFIED:
                setupTeleportLocally(player.uuid(), target);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + target.type());
        }
    }
}
