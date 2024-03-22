package it.einjojo.nucleoflex.player;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * PlayerManager interface
 */
public interface PlayerManager {
    Optional<NFPlayer> player(UUID uniqueId);

    CompletableFuture<Optional<NFPlayer>> playerAsync(UUID uniqueId);

    Optional<NFPlayer> playerByName(String name);

    CompletableFuture<Optional<NFPlayer>> playerByNameAsync(String name);

    Optional<NFOfflinePlayer> offlinePlayer(UUID uniqueId);

    CompletableFuture<Optional<NFOfflinePlayer>> offlinePlayerAsync(UUID uniqueId);

    Optional<NFOfflinePlayer> offlinePlayerByName(String name);

    CompletableFuture<Optional<NFOfflinePlayer>> offlinePlayerByNameAsync(String name);

    void update(NFOfflinePlayer player);

    void updateAsync(NFOfflinePlayer player);

}
