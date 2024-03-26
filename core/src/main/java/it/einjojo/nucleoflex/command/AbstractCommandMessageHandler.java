package it.einjojo.nucleoflex.command;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import it.einjojo.nucleoflex.api.InternalAPI;
import it.einjojo.nucleoflex.api.broker.ChannelMessage;
import it.einjojo.nucleoflex.api.broker.ChannelReceiver;
import it.einjojo.nucleoflex.api.broker.MessageProcessor;

import java.util.UUID;

//TODO
public abstract class AbstractCommandMessageHandler implements MessageProcessor {
    public static final String COMMAND_MESSAGE_ID = "cmd";
    private final InternalAPI internal;
    protected boolean registered;

    protected AbstractCommandMessageHandler(InternalAPI internal) {
        this.internal = internal;
        register();
    }

    public void register() {
        if (registered) return;
        internal.brokerService().registerMessageProcessor(this);
        registered = true;
    }

    public void unregister() {
        if (!registered) return;
        internal.brokerService().unregisterMessageProcessor(this);
        registered = false;
    }

    @Override
    public void processMessage(ChannelMessage message) {
        if (!message.messageTypeID().equals(COMMAND_MESSAGE_ID)) return;
        ByteArrayDataInput input = ByteStreams.newDataInput(message.content().getBytes());
        int typeOrdinal = input.readInt();
        CommandType type = CommandType.values()[typeOrdinal];
        String target = input.readUTF();
        String command = input.readUTF();
        processCommand(command, type, target);
    }

    protected ChannelMessage constructChannelMessage(String command, CommandType type, String target, ChannelReceiver receiver) {
        Preconditions.checkNotNull(command, "Command cannot be null");
        Preconditions.checkNotNull(type, "Type cannot be null");
        Preconditions.checkNotNull(target, "Target cannot be null");
        ByteArrayDataOutput payload = ByteStreams.newDataOutput();
        payload.writeInt(type.ordinal());
        payload.writeUTF(target);
        payload.writeUTF(command);
        return ChannelMessage.builder()
                .channel(internal.server().serverName())
                .messageTypeID(COMMAND_MESSAGE_ID)
                .content(new String(payload.toByteArray()))
                .recipient(receiver).build();

    }

    /**
     * Send a command to a group that will be executed by each service in the group
     *
     * @param groupName the name of the group
     * @param command   the command to send
     */
    public void sendGroupCommand(String groupName, String command) {

        ChannelMessage message = constructChannelMessage(command, CommandType.GROUP, groupName, ChannelReceiver.group(groupName));
        internal.brokerService().publish(message);
        if (internal.server().groupName().equals(groupName)) {
            executeCommandAsServer(command);
        }
    }

    /**
     * Send a command to a server that will be executed as the server
     *
     * @param serverName the server name to send the command to
     * @param command    the command to send
     */
    public void sendServerCommand(String serverName, String command) {
        if (internal.server().serverName().equals(serverName)) { // Server will not receive messages from itself
            executeCommandAsServer(command);
        } else {
            ChannelMessage message = constructChannelMessage(command, CommandType.SERVER, serverName, ChannelReceiver.service(serverName));
            internal.brokerService().publish(message);
        }
    }

    /**
     * Send a command to the server that will be executed as the player
     *
     * @param player           the player to send the command to
     * @param playerServerName the server name of the player to send the command to
     * @param command          the command to send
     */
    public void sendPlayerCommand(UUID player, String playerServerName, String command) {
        if (internal.server().serverName().equals(playerServerName)) { // Server will not receive messages from itself
            executeCommandAsPlayer(command, player);
        } else {
            ChannelMessage message = constructChannelMessage(command, CommandType.PLAYER, player.toString(), ChannelReceiver.service(playerServerName));
            internal.brokerService().publish(message);
        }

    }


    /**
     * Implementation specific command processing
     *
     * @param command the command to process
     * @param type    the type of the command
     * @param target  the target of the command
     */
    protected void processCommand(String command, CommandType type, String target) {
        switch (type) {
            case GROUP, SERVER:
                executeCommandAsServer(command);
                break;
            case PLAYER:
                UUID player = UUID.fromString(target);
                executeCommandAsPlayer(command, player);
                break;
        }
    }

    protected abstract void executeCommandAsPlayer(String command, UUID player);

    protected abstract void executeCommandAsServer(String command);


    /**
     * The type of the command
     */
    public enum CommandType {
        GROUP,
        SERVER,
        PLAYER
    }

}
