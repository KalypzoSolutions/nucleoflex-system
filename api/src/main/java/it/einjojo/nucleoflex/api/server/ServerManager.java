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
     * @return the servers
     */
    CompletableFuture<Collection<Server>> serversAsync();
}
