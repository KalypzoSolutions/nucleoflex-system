package it.einjojo.nucleoflex.api;

import it.einjojo.nucleoflex.api.broker.BrokerService;
import it.einjojo.nucleoflex.api.broker.RequestService;
import it.einjojo.nucleoflex.api.log.LogManager;
import it.einjojo.nucleoflex.command.AbstractCommandMessageHandler;
import it.einjojo.nucleoflex.player.PlayerNameCache;
import it.einjojo.nucleoflex.server.GroupFactory;
import it.einjojo.nucleoflex.server.ServerFactory;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface InternalAPI extends NucleoFlexAPI {


    PlayerNameCache playerNameCache();

    LogManager logManager();

    BrokerService brokerService();

    RequestService requestService();

    ServerFactory serverFactory();

    GroupFactory groupFactory();

    AbstractCommandMessageHandler commandMessageHandler();


}
