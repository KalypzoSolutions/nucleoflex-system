package it.einjojo.nucleoflex.server;

import it.einjojo.nucleoflex.api.server.Server;
import it.einjojo.nucleoflex.api.server.NetworkManager;
import it.einjojo.nucleoflex.server.factory.GroupFactory;
import it.einjojo.nucleoflex.server.factory.ServerFactory;

/**
 * This abstract class provides a base for implementing the ServerNetworkManager interface.
 * It includes a GroupFactory and a ServerFactory for creating Group and Server objects.
 * It also provides methods for registering and unregistering servers, and checking if a server is registered.
 */
public abstract class AbstractNetworkManager implements NetworkManager {

    protected final GroupFactory groupFactory;
    protected final ServerFactory factory;

    /**
     * Constructor for the AbstractNetworkManager class.
     * @param groupFactory The GroupFactory instance used to create Group objects.
     * @param factory The ServerFactory instance used to create Server objects.
     */
    protected AbstractNetworkManager(GroupFactory groupFactory, ServerFactory factory) {
        this.groupFactory = groupFactory;
        this.factory = factory;
    }

    /**
     * Abstract method for registering a server.
     * @param server The Server object to be registered.
     */
    abstract void registerServer(Server server);

    /**
     * Abstract method for unregistering a server.
     * @param server The Server object to be unregistered.
     */
    abstract void unregisterServer(Server server);

    /**
     * Checks if a server is registered.
     * @param server The Server object to check.
     * @return true if the server is registered, false otherwise.
     */
    public boolean isServerRegistered(Server server) {
        return isServerRegistered(server.serverName());
    }

    /**
     * Abstract method for checking if a server is registered by its name.
     * @param serverName The name of the server to check.
     * @return true if the server is registered, false otherwise.
     */
    abstract boolean isServerRegistered(String serverName);
}