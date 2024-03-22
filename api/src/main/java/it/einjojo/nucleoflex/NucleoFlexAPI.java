package it.einjojo.nucleoflex;

import it.einjojo.nucleoflex.messages.MessageManager;
import it.einjojo.nucleoflex.player.PlayerManager;
import it.einjojo.nucleoflex.server.ServerNetworkManager;

/**
 * NucleoFlexAPI
 */
public interface NucleoFlexAPI {
    /**
     * @return the message manager that manages the messages
     */
    MessageManager messageManager();

    /**
     * @return the player manager that manages the players
     */
    PlayerManager playerManager();

    /**
     * @return the network manager that manages the server connections
     */
    ServerNetworkManager networkManager();
}
