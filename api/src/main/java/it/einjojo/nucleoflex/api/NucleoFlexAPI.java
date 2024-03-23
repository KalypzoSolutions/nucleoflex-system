package it.einjojo.nucleoflex.api;

import it.einjojo.nucleoflex.api.messages.MessageContainer;
import it.einjojo.nucleoflex.api.player.PlayerManager;
import it.einjojo.nucleoflex.api.server.ServerNetworkManager;

/**
 * NucleoFlexAPI
 */
public interface NucleoFlexAPI {
    /**
     * @return the message manager that manages the messages
     */
    MessageContainer messageManager();

    /**
     * @return the player manager that manages the players
     */
    PlayerManager playerManager();

    /**
     * @return the network manager that manages the server connections
     */
    ServerNetworkManager networkManager();
}
