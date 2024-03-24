package it.einjojo.nucleoflex.api.impl;

import it.einjojo.nucleoflex.api.player.NFPlayer;
import it.einjojo.nucleoflex.api.server.ProxyServer;
import it.einjojo.nucleoflex.api.server.Server;
import it.einjojo.nucleoflex.api.world.Position;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ImplNFPlayer extends ImplNFOfflinePlayer implements NFPlayer {
    public ImplNFPlayer(UUID uuid, String name, long firstJoin, long lastJoin) {
        super(uuid, name, firstJoin, lastJoin);
    }

    @Override
    public void executeCommand(String command) {

    }

    @Override
    public String serverName() {
        return null;
    }

    @Override
    public String proxyName() {
        return null;
    }

    @Override
    public Server server() {
        return null;
    }

    @Override
    public ProxyServer proxy() {
        return null;
    }

    @Override
    public CompletableFuture<Position> positionAsync() {
        return null;
    }

    @Override
    public void teleport(Position position) {

    }

    @Override
    public void sendMessage(net.kyori.adventure.text.Component message) {

    } //TODO: Implement
}
