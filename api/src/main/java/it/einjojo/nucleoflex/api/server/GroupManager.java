package it.einjojo.nucleoflex.api.server;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface GroupManager {
    /**
     * @param groupName the name of the group
     * @return the group with the given name
     */
    Optional<Group> groupByName(String groupName);

    /**
     * @param groupName the name of the group
     * @return the group with the given name
     */
    CompletableFuture<Optional<Group>> groupByNameAsync(String groupName);

    /**
     * @return the registered groups
     */
    Collection<Group> groups();

    /**
     * @return the registered groups
     */
    CompletableFuture<Collection<Group>> groupAsync();

    void registerGroup(String groupName);
    void unregisterGroup(String groupName);
    boolean isGroupRegistered(String groupName);

}
