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
import it.einjojo.nucleoflex.player.handler.AbstractPositionHandler;
import it.einjojo.nucleoflex.player.handler.PermissionHandler;
import it.einjojo.nucleoflex.player.handler.PlayerSendMessageHandler;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ImplNFPlayer extends ImplNFOfflinePlayer implements NFPlayer { //TODO
    protected final transient AbstractCommandMessageHandler commandMessageHandler;
    protected final transient PlayerSendMessageHandler sendMessageHandler;
    protected final transient AbstractPositionHandler positionHandler;
    protected final transient ProxyServerManager proxyServerManager;
    protected final transient ServerManager serverManager;
    protected String connectedServer;
    protected String connectedProxy;


    public ImplNFPlayer(PlayerDataSnapshot dataSnapshot,
                        EconomyManager economyManager,
                        AbstractCommandMessageHandler commandMessageHandler,
                        AbstractPositionHandler positionHandler,
                        PlayerManager playerManager,
                        ProxyServerManager proxyServerManager,
                        ServerManager serverManager,
                        PermissionHandler permissionHandler,
                        PlayerSendMessageHandler sendMessageHandler) {
        super(dataSnapshot, playerManager, economyManager, permissionHandler);
        this.commandMessageHandler = commandMessageHandler;
        this.positionHandler = positionHandler;
        this.proxyServerManager = proxyServerManager;
        this.serverManager = serverManager;
        this.sendMessageHandler = sendMessageHandler;
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
        positionHandler.teleport(this, position);
    }

    @Override
    public void sendMessage(net.kyori.adventure.text.Component message) {

    }
}
