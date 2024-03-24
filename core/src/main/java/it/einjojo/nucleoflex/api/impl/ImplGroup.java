package it.einjojo.nucleoflex.api.impl;

import com.google.common.collect.ImmutableSet;
import it.einjojo.nucleoflex.api.player.NFPlayer;
import it.einjojo.nucleoflex.api.server.Group;
import it.einjojo.nucleoflex.api.server.Server;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class ImplGroup implements Group {
    private final String groupName;
    private Set<String> servers;
    private Set<UUID> players;

    public ImplGroup(String groupName) {
        this.groupName = groupName;
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
        return null;
    }

    @Override
    public boolean contains(String serverName) {
        return servers.contains(serverName);
    }

    @Override
    public void executeCommand(String command) {

    }

    @Override
    public int playerCount() {
        return players.size();
    }

    @Override
    public boolean containsPlayer(UUID playerUUID) {
        return players.contains(playerUUID);
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
