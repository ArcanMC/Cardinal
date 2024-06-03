package fr.arcanmc.cardinal.server.commands.player;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.command.CommandExecutor;
import fr.arcanmc.cardinal.api.player.CardinalPlayer;
import fr.arcanmc.cardinal.server.ServerService;
import fr.arcanmc.cardinal.server.player.PlayerManager;

public class PlayerInfoCommand implements CommandExecutor {
    @Override
    public void execute(String[] args) {
        if (args[0].equalsIgnoreCase("player") || args[0].equalsIgnoreCase("players")) {

            if (args.length == 3 && args[1].equalsIgnoreCase("info")) {
                Cardinal cardinal = Cardinal.getInstance();
                PlayerManager playerManager = ServerService.get().getPlayerManager();

                if (playerManager.getPlayers().isEmpty()) {
                    cardinal.getLogger().info("No players connected.");
                    return;
                }

                String playerName = args[2];

                if (playerManager.getPlayer(playerName) == null) {
                    cardinal.getLogger().info("Player not found.");
                    return;
                }

                CardinalPlayer player = playerManager.getPlayer(playerName);

                cardinal.getLogger().info("[PLAYERS] Information about " + playerName + ":");
                cardinal.getLogger().info("Name: " + player.getName());
                cardinal.getLogger().info("UUID: " + player.getUuid());
                cardinal.getLogger().info("Current Server: " + player.getCurrentServer());
                cardinal.getLogger().info("Bungee ID: " + player.getBungeeId());
            }

        }
    }
}
