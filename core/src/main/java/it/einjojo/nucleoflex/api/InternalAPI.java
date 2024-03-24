package it.einjojo.nucleoflex.api;

import it.einjojo.nucleoflex.api.player.PlayerContainer;
import it.einjojo.nucleoflex.api.player.PlayerContainerManager;
import it.einjojo.nucleoflex.command.CommandExecutorFactory;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface InternalAPI extends NucleoFlexAPI {
    CommandExecutorFactory commandExecutorFactory();

    PlayerContainerManager playerContainerManager();
}
