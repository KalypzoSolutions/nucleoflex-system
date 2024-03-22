package it.einjojo.nucleoflex.api.server;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ServerNetworkManager {
    /**
     * @param groupName the name of the group
     * @return the group with the given name
     */
    Optional<Group> groupByName(String groupName);
    CompletableFuture<Optional<Group>> groupByNameAsync(String groupName);

    /**
     * @param serverName the name of the server
     * @return the server with the given name
     */
    Optional<Server> serverByName(String serverName);

    CompletableFuture<Optional<Server>> serverByNameAsync(String serverName);

    /**
     * @param proxyName the name of the proxy
     * @return the proxy with the given name
     */
    Optional<ProxyServer> proxyByName(String proxyName);

    CompletableFuture<Optional<ProxyServer>> proxyByNameAsync(String proxyName);

    /**
     * @return the groups
     */
    Collection<Group> groups();

    CompletableFuture<Collection<Group>> groupAsync();
    /**
     * @return the servers
     */
    Collection<Server> servers();
    CompletableFuture<Collection<Server>> serversAsync();
    /**
     * @return the proxies
     */
    Collection<ProxyServer> proxies();
    CompletableFuture<Collection<ProxyServer>> proxiesAsync();

}
