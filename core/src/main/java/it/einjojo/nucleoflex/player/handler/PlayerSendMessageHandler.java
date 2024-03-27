package it.einjojo.nucleoflex.player.handler;

import it.einjojo.nucleoflex.api.player.NFPlayer;

import java.util.UUID;

public interface PlayerSendMessageHandler {


    /**
     * @param player player to send the message to
     * @param message message to send
     */
    void sendMessage(NFPlayer player, String message);

    /**
     * @param sender UUID of the sender
     * @param message message to send
     */
    void sendMessage(UUID sender, String serverName, String message);

}
