package fr.arcanmc.cardinal.server.commands.player;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.command.CommandExecutor;
import fr.arcanmc.cardinal.server.ServerService;
import fr.arcanmc.cardinal.server.player.PlayerManager;

public class PlayerListCommand implements CommandExecutor {
    @Override
    public void execute(String[] args) {
        if (args.length == 2 && args[0].equalsIgnoreCase("player") && args[1].equalsIgnoreCase("list")) {

            Cardinal cardinal = Cardinal.getInstance();
            PlayerManager playerManager = ServerService.get().getPlayerManager();

            if (playerManager.getPlayers().isEmpty()) {
                cardinal.getLogger().info("[PLAYER] No players connected");
                return;
            }
            cardinal.getLogger().info("[CLIENT] There is actually " + playerManager.getPlayers().size() + " players connected");

        }
    }
}
