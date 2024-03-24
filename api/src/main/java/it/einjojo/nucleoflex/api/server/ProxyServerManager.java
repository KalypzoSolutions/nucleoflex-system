package it.einjojo.nucleoflex.api.server;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ProxyServerManager {

    /**
     * @param proxyName the name of the proxy
     * @return the proxy with the given name
     */
    Optional<ProxyServer> proxyByName(String proxyName);

    /**
     * @param proxyName the name of the proxy
     * @return the proxy with the given name
     */
    CompletableFuture<Optional<ProxyServer>> proxyByNameAsync(String proxyName);

    /**
     * @return the proxies
     */
    Collection<ProxyServer> proxies();

    /**
     * @return the proxies
     */
    CompletableFuture<Collection<ProxyServer>> proxiesAsync();

}

