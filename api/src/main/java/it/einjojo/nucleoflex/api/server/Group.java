package it.einjojo.nucleoflex.api.server;

import it.einjojo.nucleoflex.api.command.CommandExecutor;

import java.util.Collection;

public interface Group extends CommandExecutor {
    String groupName();

    /**
     * @return the children names (server) of this group
     */
    Collection<String> serverNames();

    /**
     * @return the children (server) of this group
     */
    Collection<Server> servers();

    boolean contains(String serverName);
}
