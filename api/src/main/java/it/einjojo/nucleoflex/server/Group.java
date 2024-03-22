package it.einjojo.nucleoflex.server;

import it.einjojo.nucleoflex.command.CommandExecutor;

import java.util.Collection;

public interface Group extends CommandExecutor {
    String groupName();

    /**
     * @return the children names (server) of this group
     */
    Collection<String> childrenNames();

    /**
     * @return the children (server) of this group
     */
    Collection<Server> children();
}
