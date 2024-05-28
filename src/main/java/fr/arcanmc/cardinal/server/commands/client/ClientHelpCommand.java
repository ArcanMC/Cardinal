package fr.arcanmc.cardinal.server.commands.client;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.command.CommandExecutor;

public class ClientHelpCommand implements CommandExecutor {
    @Override
    public void execute(String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("client")) {

            Cardinal cardinal = Cardinal.getInstance();

            cardinal.getLogger().info("[CLIENT] List of commands:");
            cardinal.getLogger().info("client - Show this message");
            cardinal.getLogger().info("client list - List all connected clients");
            cardinal.getLogger().info("client info <id> - Show client information");

        }
    }
}
