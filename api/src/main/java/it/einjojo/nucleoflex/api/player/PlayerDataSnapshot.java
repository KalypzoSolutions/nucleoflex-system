package it.einjojo.nucleoflex.api.player;

import java.util.UUID;

/**
 * Represents a player data at a specific point in time -> For example the result of a query
 * Used to encapsulate data into an object so that it can be passed around easily
 */
public interface PlayerDataSnapshot {

    UUID uuid();

    String name();

    long firstJoin();

    long lastJoin();

    boolean isOnline();


}
