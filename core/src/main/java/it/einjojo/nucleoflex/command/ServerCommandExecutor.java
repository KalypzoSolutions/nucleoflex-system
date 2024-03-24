package it.einjojo.nucleoflex.command;

import it.einjojo.nucleoflex.api.command.CommandExecutor;

public class ServerCommandExecutor implements CommandExecutor {
    private final String targetServer;

    public ServerCommandExecutor(String targetServer) {
        this.targetServer = targetServer;
    }

    @Override
    public void executeCommand(String command) {

    }
}
