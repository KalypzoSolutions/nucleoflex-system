package it.einjojo.nucleoflex.api.player;

import it.einjojo.nucleoflex.api.world.Position;
import it.einjojo.nucleoflex.api.command.CommandExecutor;
import it.einjojo.nucleoflex.api.server.ProxyServer;
import it.einjojo.nucleoflex.api.server.Server;
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
     * Teleports the player to the given position.
     * @param position the position to teleport the player to.
     */
    void teleport(Position position);

    /**
     * @param message the message to send.
     */
    void sendMessage(Component message);

}
