package it.einjojo.nucleoflex.api.impl;

import it.einjojo.nucleoflex.api.log.InternalLogger;
import it.einjojo.nucleoflex.api.log.LogManager;
import it.einjojo.nucleoflex.api.server.Group;
import it.einjojo.nucleoflex.api.server.NetworkManager;
import it.einjojo.nucleoflex.api.server.ProxyServer;
import it.einjojo.nucleoflex.api.server.Server;
import it.einjojo.nucleoflex.server.GroupFactory;
import it.einjojo.nucleoflex.server.ServerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * NetworkManager implementation
 * <p> This class is responsible for managing the network, including servers, proxies, and groups. </p>
 */
public abstract class ImplNetworkManager implements NetworkManager {
    private final JedisPool pool;
    private final ServerFactory serverFactory;
    private final GroupFactory groupFactory;
    private final InternalLogger logger;

    protected ImplNetworkManager(JedisPool pool, ServerFactory serverFactory, GroupFactory groupFactory) {
        this.pool = pool;
        this.serverFactory = serverFactory;
        this.groupFactory = groupFactory;
        logger = LogManager.getLogger(getClass());
    }

    @Override
    public Optional<Group> groupByName(String groupName) {
        try (Jedis jedis = pool.getResource()) {
            Set<String> groupMembers = jedis.smembers(REDIS_PREFIX.GROUPS + groupName);
            if (groupMembers.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(groupFactory.createGroup(groupName, groupMembers));
        } catch (Exception e) {
            logger.severe("Error while fetching group by name: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public CompletableFuture<Optional<Group>> groupByNameAsync(String groupName) {
        return CompletableFuture.supplyAsync(() -> groupByName(groupName));
    }

    @Override
    public Collection<Group> groups() {
        LinkedList<Group> groups = new LinkedList<>();
        try (Jedis jedis = pool.getResource()) {
            Set<String> groupNames = jedis.keys(REDIS_PREFIX.GROUPS + "*");
            for (String groupName : groupNames) {
                Set<String> groupMembers = jedis.smembers(groupName);
                groups.add(groupFactory.createGroup(groupName, groupMembers));
            }
        } catch (Exception e) {
            logger.severe("Error while fetching groups: " + e.getMessage());
        }
        return groups;
    }

    @Override
    public CompletableFuture<Collection<Group>> groupAsync() {
        return CompletableFuture.supplyAsync(this::groups);
    }


    // PROXIES
    @Override
    public CompletableFuture<Optional<ProxyServer>> proxyByNameAsync(String proxyName) {
        return CompletableFuture.supplyAsync(() -> proxyByName(proxyName));
    }

    @Override
    public Optional<ProxyServer> proxyByName(String proxyName) {
        try (Jedis jedis = pool.getResource()) {
            boolean exists = jedis.sismember(REDIS_PREFIX.PROXIES, proxyName);
            if (!exists) {
                return Optional.empty();
            }
            return Optional.of(serverFactory.createProxyServer(proxyName));
        } catch (Exception e) {
            logger.severe("Error while fetching proxy by name: " + e.getMessage());
            return Optional.empty();

        }
    }

    @Override
    public Collection<ProxyServer> proxies() {
        LinkedList<ProxyServer> servers = new LinkedList<>();
        try (Jedis jedis = pool.getResource()) {
            Set<String> proxyNames = jedis.smembers(REDIS_PREFIX.PROXIES);
            for (String proxyName : proxyNames) {
                servers.add(serverFactory.createProxyServer(proxyName));
            }
        } catch (Exception e) {
            logger.severe("Error while fetching proxies: " + e.getMessage());
        }
        return servers;
    }

    @Override
    public CompletableFuture<Collection<ProxyServer>> proxiesAsync() {
        return CompletableFuture.supplyAsync(this::proxies);
    }

    @Override
    public void registerProxy(ProxyServer proxyServer) {
        try (Jedis jedis = pool.getResource()) {
            jedis.sadd(REDIS_PREFIX.PROXIES, proxyServer.proxyName());
        } catch (Exception e) {
            logger.severe("Error while registering proxy: " + e.getMessage());
        }
    }

    @Override
    public void unregisterProxy(String proxyName) {
        try (Jedis jedis = pool.getResource()) {
            jedis.srem(REDIS_PREFIX.PROXIES, proxyName);
        } catch (Exception e) {
            logger.severe("Error while unregistering proxy: " + e.getMessage());
        }
    }

    // Server
    @Override
    public Optional<Server> serverByName(String serverName) {
        try (Jedis jedis = pool.getResource()) {
            String serverGroup = jedis.get(REDIS_PREFIX.SERVERS + serverName);
            if (serverGroup == null) {
                return Optional.empty();
            }
            return Optional.of(serverFactory.createServer(serverName, serverGroup));
        }
    }

    @Override
    public CompletableFuture<Optional<Server>> serverByNameAsync(String serverName) {
        return CompletableFuture.supplyAsync(() -> serverByName(serverName));
    }

    @Override
    public Collection<Server> servers() {
        LinkedList<Server> servers = new LinkedList<>();
        try (Jedis jedis = pool.getResource()) {
            Set<String> serverNames = jedis.keys(REDIS_PREFIX.SERVERS + "*");
            for (String serverName : serverNames) {
                String serverGroup = jedis.get(serverName);
                servers.add(serverFactory.createServer(serverName, serverGroup));
            }
        } catch (Exception e) {
            logger.severe("Error while fetching servers: " + e.getMessage());
        }
        return servers;
    }

    @Override
    public CompletableFuture<Collection<Server>> serversAsync() {
        return CompletableFuture.supplyAsync(this::servers);
    }

    @Override
    public void registerServer(Server server) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(REDIS_PREFIX.SERVERS + server.serverName(), server.groupName());
        } catch (Exception e) {
            logger.severe("Error while registering server: " + e.getMessage());
        }
    }

    @Override
    public void unregisterServer(String serverName) {
        try (Jedis jedis = pool.getResource()) {
            jedis.del(REDIS_PREFIX.SERVERS + serverName);
        } catch (Exception e) {
            logger.severe("Error while unregistering server: " + e.getMessage());
        }
    }

    public interface REDIS_PREFIX {
        String GROUPS = "groups:";
        String PROXIES = "proxies";
        String SERVERS = "servers:";
    }
}