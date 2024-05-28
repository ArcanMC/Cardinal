package fr.arcanmc.cardinal.server.commands.instances;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.command.CommandExecutor;
import fr.arcanmc.cardinal.server.ServerService;
import fr.arcanmc.cardinal.server.game.GameManager;

public class InstanceStopCommand implements CommandExecutor {
    @Override
    public void execute(String[] args) {
        if (args.length == 3 && args[0].equalsIgnoreCase("instances") && args[1].equalsIgnoreCase("stop")) {

            Cardinal cardinal = Cardinal.getInstance();
            GameManager instancesManager = ServerService.get().getGameManager();

            String name = args[2];

            if (name != null) {
                cardinal.getLogger().info("[INSTANCES] Attempting to stop instance " + name + "...");
                instancesManager.stopInstance(name);
            }

        }

    }
}
