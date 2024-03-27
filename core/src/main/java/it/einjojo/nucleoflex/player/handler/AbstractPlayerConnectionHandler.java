package it.einjojo.nucleoflex.player.handler;

import java.util.UUID;

public abstract class AbstractPlayerConnectionHandler {
    /**
     * @param playerUniqueId the unique id of the player.
     * @return the name of the server the player is connected to.
     */
    public abstract String connectedServer(UUID playerUniqueId);

    /**
     * Connects the player to the specified server.
     *
     * @param playerUniqueId the unique id of the player.
     * @param serverName     the name of the server to connect to.
     */
    public abstract void connectServer(UUID playerUniqueId, String serverName);

    /**
     * Connects the player to the specified group.
     *
     * @param playerUniqueId the unique id of the player.
     * @param groupName      the name of the group to connect to.
     */
    public abstract void connectGroup(UUID playerUniqueId, String groupName);
}
