package it.einjojo.nucleoflex.api.impl;

import it.einjojo.nucleoflex.api.player.PlayerDataSnapshot;

import java.util.UUID;

public record ImplPlayerDataSnapshot(UUID uuid, String name, long firstJoin, long lastJoin,
                                     boolean isOnline) implements PlayerDataSnapshot {

}
