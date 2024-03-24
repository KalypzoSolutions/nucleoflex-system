package it.einjojo.nucleoflex.command;

import it.einjojo.nucleoflex.api.command.CommandExecutor;
import it.einjojo.nucleoflex.api.player.NFPlayer;
import it.einjojo.nucleoflex.api.server.Group;
import it.einjojo.nucleoflex.api.server.Server;

import java.util.UUID;

public class CommandExecutorFactory {
    public CommandExecutor executor(Object object) {
        if (object instanceof Server server) {
            return serverExecutor(server.serverName());
        }
        if (object instanceof NFPlayer player) {
            return playerExecutor(player.uuid());
        }
        if (object instanceof Group group) {
            return groupExecutor(group.groupName());
        }
        throw new IllegalArgumentException("Invalid object provided! No executor found!");
    }

    public PlayerCommandExecutor playerExecutor(UUID uuid) {
        return new PlayerCommandExecutor(uuid);
    }

    public ServerCommandExecutor serverExecutor(String serverName) {
        return new ServerCommandExecutor(serverName);
    }

    public GroupCommandExecutor groupExecutor(String groupName) {
        return new GroupCommandExecutor(groupName);
    }

}
