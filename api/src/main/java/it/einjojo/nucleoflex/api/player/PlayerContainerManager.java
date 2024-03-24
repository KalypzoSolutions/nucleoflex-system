package it.einjojo.nucleoflex.api.player;

public interface PlayerContainerManager {
    PlayerContainer groupContainer(String groupName);
    PlayerContainer serverContainer(String serverName);

}
