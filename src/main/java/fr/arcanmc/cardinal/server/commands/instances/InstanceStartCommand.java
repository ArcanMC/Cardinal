package fr.arcanmc.cardinal.server.commands.instances;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.command.CommandExecutor;
import fr.arcanmc.cardinal.server.ServerService;
import fr.arcanmc.cardinal.server.game.GameManager;

public class InstanceStartCommand implements CommandExecutor {
    @Override
    public void execute(String[] args) {
        if (args.length == 4 && args[0].equalsIgnoreCase("instances") && args[1].equalsIgnoreCase("start")) {

            Cardinal cardinal = Cardinal.getInstance();
            GameManager instancesManager = ServerService.get().getGameManager();

            String type = args[2];
            int amount = Integer.parseInt(args[3]);

            cardinal.getLogger().info("[INSTANCES] Attempting to start [" + amount + "] instance(s) of type [" + type + "]...");
            instancesManager.startGameInstance(type, amount, null);

        }
    }
}
