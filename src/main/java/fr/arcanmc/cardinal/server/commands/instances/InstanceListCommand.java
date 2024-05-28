package fr.arcanmc.cardinal.server.commands.instances;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.command.CommandExecutor;
import fr.arcanmc.cardinal.api.game.GameInstance;
import fr.arcanmc.cardinal.server.ServerService;
import fr.arcanmc.cardinal.server.game.GameManager;

public class InstanceListCommand implements CommandExecutor {

    @Override
    public void execute(String[] args) {
        if (args.length == 2 && args[0].equalsIgnoreCase("instances") && args[1].equalsIgnoreCase("list")) {
            Cardinal cardinal = Cardinal.getInstance();
            GameManager instancesManager = ServerService.get().getGameManager();

            cardinal.getConsole().sendMessage("[INSTANCES] List of instances:");
            if (instancesManager.getGames().isEmpty()) {
                cardinal.getConsole().sendMessage("   -> No instances");
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                for (String serverName : instancesManager.getGames()) {
                    GameInstance gameServer = instancesManager.getGameInstance(serverName);
                    if (gameServer != null) {
                        stringBuilder.append("[").append(gameServer.getName()).append(" - ").append(gameServer.getClientId()).append("]").append(", ");
                    }
                }
                cardinal.getLogger().info("   -> " + stringBuilder.toString());
            }
        }
    }
}
