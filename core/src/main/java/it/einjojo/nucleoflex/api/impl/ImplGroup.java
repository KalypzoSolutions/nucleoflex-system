package it.einjojo.nucleoflex.api.impl;

import com.google.common.collect.ImmutableSet;
import it.einjojo.nucleoflex.api.command.CommandExecutor;
import it.einjojo.nucleoflex.api.player.NFPlayer;
import it.einjojo.nucleoflex.api.player.PlayerContainer;
import it.einjojo.nucleoflex.api.player.PlayerContainerManager;
import it.einjojo.nucleoflex.api.server.Group;
import it.einjojo.nucleoflex.api.server.NetworkManager;
import it.einjojo.nucleoflex.api.server.Server;
import it.einjojo.nucleoflex.command.CommandExecutorFactory;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class ImplGroup implements Group {
    private final String groupName;
    private final Set<String> servers;
    private final PlayerContainerManager playerContainerManager;
    private final NetworkManager networkManager;
    private final CommandExecutorFactory commandExecutorFactory;
    private WeakReference<CommandExecutor> commandExecutor = new WeakReference<>(null);


    public ImplGroup(String groupName, Set<String> servers, PlayerContainerManager playerContainerManager, NetworkManager networkManager, CommandExecutorFactory commandExecutorFactory) {
        this.groupName = groupName;
        this.servers = servers;
        this.playerContainerManager = playerContainerManager;
        this.networkManager = networkManager;
        this.commandExecutorFactory = commandExecutorFactory;
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
            Optional<Server> optionalServer = networkManager.serverByName(serverName);
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
        CommandExecutor content = commandExecutor.get();
        if (content == null) {
            content = commandExecutorFactory.groupExecutor(groupName());
            commandExecutor = new WeakReference<>(content);
        }
        content.executeCommand(command);
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
