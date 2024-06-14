package fr.arcanmc.cardinal.server.commands.player;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.command.CommandExecutor;

public class PlayerHelpCommand implements CommandExecutor {
    @Override
    public void execute(String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("player")) {

            Cardinal cardinal = Cardinal.getInstance();

            cardinal.getLogger().info("[PLAYERS] List of commands:");
            cardinal.getLogger().info("player - Show this message");
            cardinal.getLogger().info("player list - List all connected players (number)");
            cardinal.getLogger().info("player info <name> - Show player information");

        }
    }
}
