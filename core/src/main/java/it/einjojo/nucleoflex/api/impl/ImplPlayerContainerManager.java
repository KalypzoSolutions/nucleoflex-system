package it.einjojo.nucleoflex.api.impl;

import it.einjojo.nucleoflex.api.player.PlayerContainer;
import it.einjojo.nucleoflex.api.player.PlayerContainerManager;

//TODO
public abstract class ImplPlayerContainerManager implements PlayerContainerManager {
    @Override
    public PlayerContainer groupContainer(String groupName) {
        return null;
    }

    @Override
    public PlayerContainer serverContainer(String serverName) {
        return null;
    }
}
