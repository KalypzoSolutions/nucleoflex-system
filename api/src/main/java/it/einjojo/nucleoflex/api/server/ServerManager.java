package it.einjojo.nucleoflex.api.server;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ServerManager {
    /**
     * @param serverName the name of the server
     * @return the server with the given name
     */
    Optional<Server> serverByName(String serverName);

    /**
     * @param serverName the name of the server
     * @return the server with the given name
     */
    CompletableFuture<Optional<Server>> serverByNameAsync(String serverName);

    /**
     * @return the servers
     */
    Collection<Server> servers();

    /**
     * @param serverNames the names of the servers
     * @return the servers with the given names
     */
    Collection<Server> servers(String... serverNames);

    /**
     * @param serverNames the names of the servers
     * @return the servers with the given names
     */
    CompletableFuture<Collection<Server>> serversAsync(String... serverNames);

    /**
     * @return the servers
     */
    CompletableFuture<Collection<Server>> serversAsync();

    void registerServer(Server server);

    void unregisterServer(String serverName);
}
