package it.einjojo.nucleoflex.player;

import it.einjojo.nucleoflex.api.player.NFOfflinePlayer;
import it.einjojo.nucleoflex.api.player.NFPlayer;
import it.einjojo.nucleoflex.api.player.PlayerDataSnapshot;

public abstract class AbstractPlayerFactory {
    NFOfflinePlayer createOfflinePlayer(PlayerDataSnapshot playerData) {
        return null;
    }

    NFPlayer createPlayer(PlayerDataSnapshot data) {
        return null;
    }

}
