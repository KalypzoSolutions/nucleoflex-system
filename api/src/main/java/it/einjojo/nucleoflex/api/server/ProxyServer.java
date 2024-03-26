package it.einjojo.nucleoflex.api.server;

import it.einjojo.nucleoflex.api.player.PlayerContainer;

import java.util.UUID;

/**
 * ProxyServer interface
 */


public interface ProxyServer extends PlayerContainer, Server {
    /**
     * @return the name of the proxy
     */
    String proxyName();

    @Override
    default String serverName() {
        return proxyName();
    }

    @Override
    default void connect(UUID playerUniqueId) {
        throw new UnsupportedOperationException("Cannot connect a player to a proxy server");
    }
}
