package it.einjojo.nucleoflex.api.impl;

import com.google.common.collect.ImmutableSet;
import it.einjojo.nucleoflex.api.player.NFPlayer;
import it.einjojo.nucleoflex.api.player.PlayerContainer;
import it.einjojo.nucleoflex.api.player.PlayerContainerManager;
import it.einjojo.nucleoflex.api.server.Group;
import it.einjojo.nucleoflex.api.server.Server;
import it.einjojo.nucleoflex.api.server.ServerManager;
import it.einjojo.nucleoflex.command.AbstractCommandMessageHandler;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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
    public CompletableFuture<Collection<Server>> serversAsync() {
        return serverManager.serversAsync(servers.toArray(new String[0]));
    }

    @Override
    public Collection<Server> servers() {
        return serverManager.servers(servers.toArray(new String[0]));
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
