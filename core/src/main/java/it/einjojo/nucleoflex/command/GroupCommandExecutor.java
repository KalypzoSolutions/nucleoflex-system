package it.einjojo.nucleoflex.command;

import it.einjojo.nucleoflex.api.command.CommandExecutor;

public class GroupCommandExecutor implements CommandExecutor {
    private final String targetGroup;

    public GroupCommandExecutor(String targetGroup) {
        this.targetGroup = targetGroup;
    }

    @Override
    public void executeCommand(String command) {

    }
}
