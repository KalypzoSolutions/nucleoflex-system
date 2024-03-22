package it.einjojo.nucleoflex.player;

import it.einjojo.nucleoflex.command.CommandExecutor;
import it.einjojo.nucleoflex.server.ProxyServer;
import it.einjojo.nucleoflex.server.Server;
import it.einjojo.nucleoflex.world.Position;
import net.kyori.adventure.text.Component;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface NFPlayer extends NFOfflinePlayer, CommandExecutor {

    @Override
    default Optional<NFPlayer> toPlayer() {
        return Optional.of(this);
    }

    /**
     * @return the name of the server where the player is connected.
     */
    String serverName();

    /**
     * @return the name of the proxy where the player is connected.
     */
    String proxyName();

    /**
     * @return the server where the player is connected.
     */
    Server server();

    /**
     * @return the proxy where the player is connected.
     */
    ProxyServer proxy();

    /**
     * @return the position of the player.
     */
    CompletableFuture<Position> positionAsync();

    /**
     * @param message the message to send.
     */
    void sendMessage(Component message);

}
