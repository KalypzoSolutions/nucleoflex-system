package it.einjojo.nucleoflex.server;

import it.einjojo.nucleoflex.api.server.ProxyServer;
import it.einjojo.nucleoflex.api.server.Server;

/**
 * This interface defines the contract for a factory that creates Server objects.
 * It provides a method to create a new Server given a server name and a group name.
 */
public interface ServerFactory {

    /**
     * Creates a new Server with the given server name and group name.
     *
     * @param serverName The name of the server to be created.
     * @param groupName  The name of the group to which the server belongs.
     * @return The newly created Server object.
     */
    Server createServer(String serverName, String groupName);

    /**
     * Creates a new ProxyServer with the given server name and default group name ("Proxy").
     *
     * @param proxyName The name of the proxy server to be created.
     * @return The newly created ProxyServer object.
     */
    ProxyServer createProxyServer(String proxyName);

}