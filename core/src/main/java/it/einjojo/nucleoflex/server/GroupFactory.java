package it.einjojo.nucleoflex.server;

import it.einjojo.nucleoflex.api.impl.ImplGroup;
import it.einjojo.nucleoflex.api.player.PlayerContainerManager;
import it.einjojo.nucleoflex.api.server.Group;
import it.einjojo.nucleoflex.api.server.ServerManager;
import it.einjojo.nucleoflex.command.AbstractCommandMessageHandler;

import java.util.Set;

/**
 * This interface defines the contract for a factory that creates Group objects.
 * It provides a method to create a new Group given a group name.
 */
public class GroupFactory {
    private final PlayerContainerManager playerContainerManager;
    private final ServerManager serverManager;
    private final AbstractCommandMessageHandler commandMessageHandler;

    public GroupFactory(
            PlayerContainerManager playerContainerManager,
            ServerManager serverManager,
            AbstractCommandMessageHandler commandMessageHandler) {
        this.playerContainerManager = playerContainerManager;
        this.serverManager = serverManager;
        this.commandMessageHandler = commandMessageHandler;
    }

    public Group createGroup(String groupName, Set<String> servers) {
        return new ImplGroup(groupName, servers, playerContainerManager, serverManager, commandMessageHandler);
    }
}