package fr.arcanmc.cardinal.server.commands.bungee;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.command.CommandExecutor;
import fr.arcanmc.cardinal.core.console.Logger;
import fr.arcanmc.cardinal.server.ServerService;
import fr.arcanmc.cardinal.server.bungeecord.BungeeManager;

public class BungeeCommand implements CommandExecutor {
    @Override
    public void execute(String[] args) {
        if (args[0].equalsIgnoreCase("bungee")) {
            Logger logger = Cardinal.getInstance().getLogger();
            BungeeManager bungeeManager = ServerService.get().getBungeeManager();

            if (bungeeManager.getBungees().isEmpty()) {
                logger.info("No bungee connected.");
                return;
            }

            logger.info("[BUNGEE] List of bungee:");
            logger.info("Count: " + bungeeManager.getBungees().size());
            StringBuilder bungees = new StringBuilder();
            bungeeManager.getBungees().stream().limit(15).forEach(bungee -> bungees.append("[").append(bungee.getId()).append("/").append(bungee.getAddress()).append(":").append(bungee.getPort()).append("]").append(", "));
            logger.info("Bungees: " + bungees.toString());
        }
    }
}
