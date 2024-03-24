package it.einjojo.nucleoflex.command;

import it.einjojo.nucleoflex.api.command.CommandExecutor;

import java.util.UUID;

public class PlayerCommandExecutor implements CommandExecutor {
    private final UUID playerUUID;

    public PlayerCommandExecutor(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    @Override
    public void executeCommand(String command) {

    }
}
