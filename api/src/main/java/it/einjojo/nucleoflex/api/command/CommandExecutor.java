package it.einjojo.nucleoflex.api.command;

public interface CommandExecutor {

    /**
     * @param command the command to execute without '/'.
     */
    void executeCommand(String command);
}
