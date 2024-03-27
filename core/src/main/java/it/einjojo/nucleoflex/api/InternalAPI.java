package it.einjojo.nucleoflex.api;

import it.einjojo.nucleoflex.api.broker.BrokerService;
import it.einjojo.nucleoflex.api.log.LogManager;
import it.einjojo.nucleoflex.api.player.PlayerContainerManager;
import it.einjojo.nucleoflex.player.PlayerNameCache;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface InternalAPI extends NucleoFlexAPI {
    PlayerContainerManager playerContainerManager();

    PlayerNameCache playerNameCache();

    LogManager logManager();

    BrokerService brokerService();


}
