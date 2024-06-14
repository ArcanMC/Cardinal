package fr.arcanmc.cardinal.server.commands.player;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.command.CommandExecutor;
import fr.arcanmc.cardinal.api.player.CardinalPlayer;
import fr.arcanmc.cardinal.server.ServerService;
import fr.arcanmc.cardinal.server.player.PlayerManager;

public class PlayerInfoCommand implements CommandExecutor {
    @Override
    public void execute(String[] args) {
        if (args.length == 3 && args[0].equalsIgnoreCase("player") && args[1].equalsIgnoreCase("info")) {

            Cardinal cardinal = Cardinal.getInstance();
            PlayerManager playerManager = ServerService.get().getPlayerManager();

            String name = args[2];

            if (playerManager.getPlayer(name) != null) {
                CardinalPlayer player = playerManager.getPlayer(name);
                cardinal.getLogger().info("[Player] Player information:");
                cardinal.getLogger().info("NAME: " + player.getName());
                cardinal.getLogger().info("UUID: " + player.getUuid().toString());
                cardinal.getLogger().info("BungeeID: " + player.getBungeeId());
                cardinal.getLogger().info("Current Server: " + player.getCurrentServer());
            } else {
                cardinal.getLogger().error("Player not found");
            }

        }
    }
}
