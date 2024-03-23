package it.einjojo.nucleoflex.server;

import it.einjojo.nucleoflex.api.server.Server;
import it.einjojo.nucleoflex.api.server.ServerNetworkManager;
import it.einjojo.nucleoflex.server.factory.GroupFactory;
import it.einjojo.nucleoflex.server.factory.ServerFactory;

public abstract class AbstractNetworkManager implements ServerNetworkManager {

    protected final GroupFactory groupFactory;
    protected final ServerFactory factory;

    protected AbstractNetworkManager(GroupFactory groupFactory, ServerFactory factory) {
        this.groupFactory = groupFactory;
        this.factory = factory;
    }

    abstract void registerServer(Server server);

    abstract void unregisterServer(Server server);

    public boolean isServerRegistered(Server server) {
        return isServerRegistered(server.serverName());
    }

    abstract boolean isServerRegistered(String serverName);
}
