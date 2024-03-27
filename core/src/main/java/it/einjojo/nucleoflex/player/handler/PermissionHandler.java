package it.einjojo.nucleoflex.player.handler;

import java.util.UUID;

public interface PermissionHandler {
    boolean hasPermission(UUID player, String permission);

}
