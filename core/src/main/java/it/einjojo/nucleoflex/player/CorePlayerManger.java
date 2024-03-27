package it.einjojo.nucleoflex.player;

import it.einjojo.nucleoflex.api.player.NFOfflinePlayer;
import it.einjojo.nucleoflex.api.player.NFPlayer;
import it.einjojo.nucleoflex.api.player.PlayerContainer;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class CorePlayerManger extends AbstractPlayerManager {


    protected CorePlayerManger(PlayerNameCache playerNameCache, AbstractPlayerFactory playerFactory) {
        super(playerNameCache, playerFactory);
    }

    @Override
    protected CompletableFuture<NFOfflinePlayer> loadOfflinePlayer(UUID uniqueId, Executor executor) {
        return null;
    }

    @Override
    public int playerCount() {
        return 0;
    }

    @Override
    public Collection<NFPlayer> players() {
        return null;
    }

    @Override
    public PlayerContainer groupContainer(String groupName) {
        return null;
    }

    @Override
    public PlayerContainer serverContainer(String serverName) {
        return null;
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
    public void update(NFOfflinePlayer player) {

    }

    @Override
    public void updateAsync(NFOfflinePlayer player) {

    }

    @Override
    protected CompletableFuture<NFPlayer> loadPlayer(UUID uniqueId, Executor executor) {
        return null;
    }
}
