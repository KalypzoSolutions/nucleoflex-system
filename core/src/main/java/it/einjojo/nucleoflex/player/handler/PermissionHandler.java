package it.einjojo.nucleoflex.player.handler;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PermissionHandler {
    boolean hasPermission(UUID player, String permission);

    CompletableFuture<Boolean> hasPermissionAsync(UUID player, String permission);

}
