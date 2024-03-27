package it.einjojo.nucleoflex.player.handler;

import it.einjojo.nucleoflex.hook.LuckPermsHook;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class LuckPermsPermissionHandler implements PermissionHandler {

    private final LuckPermsHook hook;

    public LuckPermsPermissionHandler(LuckPermsHook hook) {
        this.hook = hook;
    }


    @Override
    public boolean hasPermission(UUID player, String permission) {
        return hook.hasPermission(player, permission);
    }

    @Override
    public CompletableFuture<Boolean> hasPermissionAsync(UUID player, String permission) {
        return hook.hasPermissionAsync(player, permission);
    }
}
