package fr.arcanmc.cardinal.server.commands.player;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.command.CommandExecutor;
import fr.arcanmc.cardinal.server.ServerService;
import fr.arcanmc.cardinal.server.player.PlayerManager;

public class PlayerListCommand implements CommandExecutor {
    @Override
    public void execute(String[] args) {
        if (args[0].equalsIgnoreCase("player") || args[0].equalsIgnoreCase("players")) {

            if (args.length == 2 && args[1].equalsIgnoreCase("list")) {
                Cardinal cardinal = Cardinal.getInstance();
                PlayerManager playerManager = ServerService.get().getPlayerManager();

                if (playerManager.getPlayers().isEmpty()) {
                    cardinal.getLogger().info("No players connected.");
                    return;
                }

                cardinal.getLogger().info("[PLAYERS] List of players:");
                cardinal.getLogger().info("Count: " + playerManager.getPlayers().size());
                StringBuilder players = new StringBuilder();
                playerManager.getPlayers().stream().limit(15).forEach(player -> players.append(player.getName()).append(", "));
                cardinal.getLogger().info("Players: " + players.toString());
            }

        }
    }
}
