package it.einjojo.nucleoflex.server.factory;

import it.einjojo.nucleoflex.api.server.Group;

/**
 * This interface defines the contract for a factory that creates Group objects.
 * It provides a method to create a new Group given a group name.
 */
public interface GroupFactory {

    /**
     * Creates a new Group with the given group name.
     * @param groupName The name of the group to be created.
     * @return The newly created Group object.
     */
    Group createGroup(String groupName);

}