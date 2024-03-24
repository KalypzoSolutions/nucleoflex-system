package it.einjojo.nucleoflex.player;

import java.util.UUID;

public interface PlayerConnectionHandler {
    String getConnectedServerName(UUID playerUniqueId);
    void connect(UUID playerUniqueId, String serverName);
}
