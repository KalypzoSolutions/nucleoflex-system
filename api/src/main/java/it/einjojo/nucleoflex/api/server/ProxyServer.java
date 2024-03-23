package it.einjojo.nucleoflex.api.server;

import it.einjojo.nucleoflex.api.player.PlayerContainer;

/**
 * ProxyServer interface
 */
public interface ProxyServer extends PlayerContainer {
    /**
     * @return the name of the proxy
     */
    String proxyName();

}
