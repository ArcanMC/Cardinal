package fr.arcanmc.cardinal.server.commands.player;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.command.CommandExecutor;

public class PlayerHelpCommand implements CommandExecutor {
    @Override
    public void execute(String[] args) {
        if (args[0].equalsIgnoreCase("player") || args[0].equalsIgnoreCase("players")) {

            Cardinal cardinal = Cardinal.getInstance();

            cardinal.getLogger().info("[PLAYERS] List of commands:");
            cardinal.getLogger().info("player - Show this message");
            cardinal.getLogger().info("player list - Show the list of players (only 15 first and count)");
            cardinal.getLogger().info("player info <player> - Show information about a player");

        }
    }
}
