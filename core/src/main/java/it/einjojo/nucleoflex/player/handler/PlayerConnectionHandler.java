package it.einjojo.nucleoflex.player.handler;

import java.util.UUID;

public interface PlayerConnectionHandler {
    /**
     * @param playerUniqueId the unique id of the player.
     * @return the name of the server the player is connected to.
     */
    String connectedServer(UUID playerUniqueId);

    /**
     * Connects the player to the specified server.
     *
     * @param playerUniqueId the unique id of the player.
     * @param serverName     the name of the server to connect to.
     */
    void connectServer(UUID playerUniqueId, String serverName);

    /**
     * Connects the player to the specified group.
     *
     * @param playerUniqueId the unique id of the player.
     * @param groupName      the name of the group to connect to.
     */
    void connectGroup(UUID playerUniqueId, String groupName);
}
