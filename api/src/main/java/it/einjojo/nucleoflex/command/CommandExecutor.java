package it.einjojo.nucleoflex.command;

public interface CommandExecutor {

    /**
     * @param command the command to execute without '/'.
     */
    void executeCommand(String command);
}
