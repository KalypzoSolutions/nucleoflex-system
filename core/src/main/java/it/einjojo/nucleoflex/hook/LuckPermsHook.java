package it.einjojo.nucleoflex.hook;

import it.einjojo.nucleoflex.player.handler.PermissionHandler;

import java.util.UUID;

public class LuckPermsHook implements PermissionHandler {
    @Override
    public boolean hasPermission(UUID player, String permission) {
        return false;
    }
}
