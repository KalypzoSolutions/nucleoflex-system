package it.einjojo.nucleoflex.server;

import it.einjojo.nucleoflex.api.player.NFPlayer;
import it.einjojo.nucleoflex.api.server.Server;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractServer implements Server {

    private final String serverName;

    protected AbstractServer(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public String serverName() {
        return this.serverName;
    }

    @Override
    public int playerCount() {
        return 0;
    }

    @Override
    public int maxPlayers() {
        return 0;
    }

    @Override
    public Collection<NFPlayer> players() {
        return null;
    }

    @Override
    public CompletableFuture<Collection<NFPlayer>> playersAsync() {
        return null;
    }

    @Override
    public void connect(UUID playerUniqueId) {

    }
}
