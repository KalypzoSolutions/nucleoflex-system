package it.einjojo.nucleoflex.api;

import it.einjojo.nucleoflex.api.messages.MessageContainer;
import it.einjojo.nucleoflex.api.player.PlayerManager;
import it.einjojo.nucleoflex.api.server.NetworkManager;
import it.einjojo.nucleoflex.api.server.Server;

/**
 * NucleoFlexAPI
 */
public interface NucleoFlexAPI {
    /**
     * @return the message manager that manages the messages
     */
    MessageContainer messages();

    /**
     * @return the player manager that manages the players
     */
    PlayerManager playerManager();

    /**
     * @return the network manager that manages the server connections
     */
    NetworkManager networkManager();

    /**
     * @return the server on which the API is running
     */
    Server server();
}
