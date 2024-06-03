package fr.arcanmc.cardinal.server.commands;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.command.CommandExecutor;

public class HelpCommand implements CommandExecutor {
    @Override
    public void execute(String[] args) {
        if (args[0].equalsIgnoreCase("help")) {

            Cardinal cardinal = Cardinal.getInstance();

            cardinal.getLogger().info("[DEFAULT] List of commands:");
            cardinal.getLogger().info("help - Show this message");
            cardinal.getLogger().info("stop - Stop the server");
            cardinal.getLogger().info("client - List all client management commands");
            cardinal.getLogger().info("instances - List all instances management commands");
            cardinal.getLogger().info("player - List all player management commands");
            cardinal.getLogger().info("bungee - List all bungees connected");

        }
    }
}
