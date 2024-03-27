package it.einjojo.nucleoflex.api.impl;

import it.einjojo.nucleoflex.api.player.NFPlayer;
import it.einjojo.nucleoflex.api.player.PlayerContainer;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class ImplPlayerContainer implements PlayerContainer {
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
