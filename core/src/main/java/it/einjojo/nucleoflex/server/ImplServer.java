package it.einjojo.nucleoflex.server;

import it.einjojo.nucleoflex.api.player.NFPlayer;
import it.einjojo.nucleoflex.api.server.Group;
import it.einjojo.nucleoflex.api.server.Server;
import it.einjojo.nucleoflex.player.PlayerConnectionHandler;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ImplServer implements Server {

    private final String serverName;
    private final String groupName;

    private transient PlayerConnectionHandler playerConnectionHandler;

    protected ImplServer(String serverName, String groupName) {
        this.serverName = serverName;
        this.groupName = groupName;
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
        return null;
    }

    @Override
    public void connect(UUID playerUniqueId) {

    }

    @Override
    public int playerCount() {
        return 0;
    }

    @Override
    public boolean containsPlayer(UUID playerUUID) {
        return false;
    }

    @Override
    public boolean containsPlayer(String playerName) {
        return false;
    }

    @Override
    public Optional<NFPlayer> player(UUID playerUUID) {
        return Optional.empty();
    }

    @Override
    public Optional<NFPlayer> playerByName(String playerName) {
        return Optional.empty();
    }

    @Override
    public CompletableFuture<Optional<NFPlayer>> playerAsync(UUID playerUUID) {
        return null;
    }

    @Override
    public CompletableFuture<Optional<NFPlayer>> playerByNameAsync(String playerName) {
        return null;
    }

    @Override
    public Collection<NFPlayer> players() {
        return null;
    }

    @Override
    public CompletableFuture<Collection<NFPlayer>> playersAsync() {
        return null;
    }
}
