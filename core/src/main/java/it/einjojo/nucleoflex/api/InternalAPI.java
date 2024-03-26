package it.einjojo.nucleoflex.api;

import it.einjojo.nucleoflex.api.broker.BrokerService;
import it.einjojo.nucleoflex.api.player.PlayerContainerManager;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface InternalAPI extends NucleoFlexAPI {
    PlayerContainerManager playerContainerManager();

    BrokerService brokerService();


}
