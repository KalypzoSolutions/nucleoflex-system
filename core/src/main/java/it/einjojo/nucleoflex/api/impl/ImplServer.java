package it.einjojo.nucleoflex.api.impl;

import it.einjojo.nucleoflex.api.player.NFPlayer;
import it.einjojo.nucleoflex.api.server.Group;
import it.einjojo.nucleoflex.api.server.Server;
import it.einjojo.nucleoflex.player.PlayerConnectionHandler;
import it.einjojo.nucleoflex.player.PlayerNameCache;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class ImplServer implements Server {

    private final String serverName;
    private final String groupName;

    private transient PlayerConnectionHandler playerConnectionHandler;
    private transient PlayerNameCache playerNameCache;

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
    public Optional<NFPlayer> player(UUID playerUUID) {
        return Optional.empty();
    }


    @Override
    public Collection<NFPlayer> players() {
        return null;
    }


}
