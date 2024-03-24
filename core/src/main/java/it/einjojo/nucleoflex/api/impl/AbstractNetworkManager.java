package it.einjojo.nucleoflex.api.impl;

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



}