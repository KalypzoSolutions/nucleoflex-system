package it.einjojo.nucleoflex.api.impl;

import it.einjojo.nucleoflex.api.economy.EconomyManager;
import it.einjojo.nucleoflex.api.player.NFPlayer;
import it.einjojo.nucleoflex.api.player.PlayerDataSnapshot;
import it.einjojo.nucleoflex.api.player.PlayerManager;
import it.einjojo.nucleoflex.api.server.ProxyServer;
import it.einjojo.nucleoflex.api.server.ProxyServerManager;
import it.einjojo.nucleoflex.api.server.Server;
import it.einjojo.nucleoflex.api.server.ServerManager;
import it.einjojo.nucleoflex.api.world.Position;
import it.einjojo.nucleoflex.command.AbstractCommandMessageHandler;
import it.einjojo.nucleoflex.world.AbstractPositionHandler;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ImplNFPlayer extends ImplNFOfflinePlayer implements NFPlayer { //TODO
    protected final AbstractCommandMessageHandler commandMessageHandler;
    protected final AbstractPositionHandler positionHandler;
    protected final ProxyServerManager proxyServerManager;
    protected final ServerManager serverManager;
    protected String connectedServer;
    protected String connectedProxy;

    public ImplNFPlayer(PlayerDataSnapshot dataSnapshot,
                        EconomyManager economyManager,
                        AbstractCommandMessageHandler commandMessageHandler,
                        AbstractPositionHandler positionHandler,
                        PlayerManager playerManager,
                        ProxyServerManager proxyServerManager,
                        ServerManager serverManager) {
        super(dataSnapshot, playerManager, economyManager);
        this.commandMessageHandler = commandMessageHandler;
        this.positionHandler = positionHandler;
        this.proxyServerManager = proxyServerManager;
        this.serverManager = serverManager;
    }

    @Override
    public void executeCommand(String command) {
        commandMessageHandler.sendPlayerCommand(uuid(), serverName(), command);
    }

    @Override
    public String serverName() {
        return connectedServer;
    }

    @Override
    public String proxyName() {
        return connectedProxy;
    }

    @Override
    public Server server() {
        return serverManager.serverByName(connectedServer).orElseThrow(() -> new IllegalStateException("Server not found"));

    }

    @Override
    public Optional<NFPlayer> toPlayer() {
        return Optional.of(this);
    }

    @Override
    public ProxyServer proxy() {
        return proxyServerManager.proxyByName(connectedProxy).orElseThrow(() -> new IllegalStateException("Proxy not found"));
    }

    @Override
    public CompletableFuture<Position> positionAsync() {
        return positionHandler.requestPositionOfPlayer(uuid(), serverName());
    }

    @Override
    public void teleport(Position position) {
        positionHandler.teleport(uuid(), position);
    }

    @Override
    public void sendMessage(net.kyori.adventure.text.Component message) {

    } //TODO: Implement
}
