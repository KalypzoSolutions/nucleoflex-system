package it.einjojo.nucleoflex.api.impl;

import com.google.common.collect.ImmutableSet;
import it.einjojo.nucleoflex.api.player.NFPlayer;
import it.einjojo.nucleoflex.api.player.PlayerContainer;
import it.einjojo.nucleoflex.api.player.PlayerContainerManager;
import it.einjojo.nucleoflex.api.server.Group;
import it.einjojo.nucleoflex.api.server.NetworkManager;
import it.einjojo.nucleoflex.api.server.Server;
import it.einjojo.nucleoflex.api.server.ServerManager;
import it.einjojo.nucleoflex.command.AbstractCommandMessageHandler;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class ImplGroup implements Group {
    private final String groupName;
    private final Set<String> servers;
    private final PlayerContainerManager playerContainerManager;
    private final ServerManager serverManager;
    private final AbstractCommandMessageHandler commandMessageHandler;


    public ImplGroup(String groupName, Set<String> servers, PlayerContainerManager playerContainerManager, ServerManager serverManager, AbstractCommandMessageHandler commandHandler) {
        this.groupName = groupName;
        this.servers = servers;
        this.playerContainerManager = playerContainerManager;
        this.serverManager = serverManager;
        this.commandMessageHandler = commandHandler;
    }


    @Override
    public String groupName() {
        return groupName;
    }

    @Override
    public Collection<String> serverNames() {
        return ImmutableSet.copyOf(servers);
    }

    @Override
    public Collection<Server> servers() {
        final Server[] serverArray = new Server[servers.size()];
        int index = 0;
        for (String serverName : servers) {
            Optional<Server> optionalServer = serverManager.serverByName(serverName);
            if (optionalServer.isPresent()) {
                serverArray[index] = optionalServer.get();
                index++;
            } else {
                throw new IllegalStateException("Server " + serverName + " is not present in the network but is in group!");
            }
        }
        return ImmutableSet.copyOf(serverArray);
    }

    @Override
    public boolean contains(String serverName) {
        return servers.contains(serverName);
    }

    @Override
    public void executeCommand(String command) {
        commandMessageHandler.sendGroupCommand(groupName(), command);
    }

    @Override
    public int playerCount() {
        return playerContainer().playerCount();
    }

    @Override
    public boolean containsPlayer(UUID playerUUID) {
        return playerContainer().containsPlayer(playerUUID);
    }

    @Override
    public Optional<NFPlayer> player(UUID playerUUID) {
        return playerContainer().player(playerUUID);
    }

    @Override
    public Collection<NFPlayer> players() {
        return playerContainer().players();
    }

    protected PlayerContainer playerContainer() {
        return playerContainerManager.groupContainer(groupName());
    }
}
