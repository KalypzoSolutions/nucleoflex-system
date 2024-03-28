package it.einjojo.nucleoflex.api.server;

import it.einjojo.nucleoflex.api.command.CommandExecutor;
import it.einjojo.nucleoflex.api.player.PlayerContainer;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public interface Group extends CommandExecutor, PlayerContainer {
    String groupName();

    /**
     * @return the children names (server) of this group
     */
    Collection<String> serverNames();

    /**
     * @return the children (server) of this group
     */
    Collection<Server> servers();
    CompletableFuture<Collection<Server>> serversAsync();

    boolean contains(String serverName);
}
