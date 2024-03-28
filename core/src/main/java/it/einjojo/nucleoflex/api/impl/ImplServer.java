package it.einjojo.nucleoflex.api.impl;

import it.einjojo.nucleoflex.api.player.NFPlayer;
import it.einjojo.nucleoflex.api.player.PlayerContainer;
import it.einjojo.nucleoflex.api.player.PlayerContainerManager;
import it.einjojo.nucleoflex.api.server.Group;
import it.einjojo.nucleoflex.api.server.GroupManager;
import it.einjojo.nucleoflex.api.server.Server;
import it.einjojo.nucleoflex.player.handler.AbstractPlayerConnectionHandler;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ImplServer implements Server {

    private final String serverName;
    private final String groupName;

    private final transient GroupManager groupManager;
    private final transient AbstractPlayerConnectionHandler playerConnectionHandler;
    private final transient PlayerContainerManager playerContainerManager;

    public ImplServer(String serverName, String groupName, GroupManager groupManager, AbstractPlayerConnectionHandler playerConnectionHandler, PlayerContainerManager playerContainerManager) {
        this.serverName = serverName;
        this.groupName = groupName;
        this.groupManager = groupManager;
        this.playerConnectionHandler = playerConnectionHandler;
        this.playerContainerManager = playerContainerManager;
    }


    @Override
    public String serverName() {
        return this.serverName;
    }

    @Override
    public String groupName() {
        return this.groupName;
    }

    @Override
    public Group group() {
        return groupManager.groupByName(groupName).orElseThrow();
    }

    @Override
    public CompletableFuture<Group> groupAsync() {
        return groupManager.groupByNameAsync(groupName).thenApply(Optional::orElseThrow);
    }

    @Override
    public void connect(UUID playerUniqueId) {
        playerConnectionHandler.connectServer(playerUniqueId, serverName);
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

    PlayerContainer playerContainer() {
        return playerContainerManager.serverContainer(serverName);
    }


}
