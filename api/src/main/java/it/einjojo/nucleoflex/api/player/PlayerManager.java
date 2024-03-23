package it.einjojo.nucleoflex.api.player;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * PlayerManager interface
 */
public interface PlayerManager extends PlayerContainer {
    Optional<NFPlayer> player(UUID uniqueId);

    CompletableFuture<Optional<NFPlayer>> playerAsync(UUID uniqueId);

    Optional<NFPlayer> playerByName(String name);

    CompletableFuture<Optional<NFPlayer>> playerByNameAsync(String name);

    Optional<NFOfflinePlayer> offlinePlayer(UUID uniqueId);

    CompletableFuture<Optional<NFOfflinePlayer>> offlinePlayerAsync(UUID uniqueId);

    Optional<NFOfflinePlayer> offlinePlayerByName(String name);

    CompletableFuture<Optional<NFOfflinePlayer>> offlinePlayerByNameAsync(String name);

    boolean containsPlayer(UUID playerUUID);

    boolean containsPlayer(String playerName);




    void update(NFOfflinePlayer player);

    void updateAsync(NFOfflinePlayer player);

}
