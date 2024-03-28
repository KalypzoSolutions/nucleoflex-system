package it.einjojo.nucleoflex.economy;

import it.einjojo.nucleoflex.api.player.PlayerEconomy;

public interface PlayerEconomyObserver {
    void publishChange(PlayerEconomy playerEconomy);
}
