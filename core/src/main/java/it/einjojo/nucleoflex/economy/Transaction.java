package it.einjojo.nucleoflex.economy;

import java.util.UUID;

public record Transaction(int id, UUID playerUuid, long deltaBalance, long timestamp) {

}
