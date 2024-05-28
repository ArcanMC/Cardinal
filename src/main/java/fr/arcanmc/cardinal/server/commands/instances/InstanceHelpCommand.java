package fr.arcanmc.cardinal.server.commands.instances;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.command.CommandExecutor;

public class InstanceHelpCommand implements CommandExecutor {
    @Override
    public void execute(String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("instances")) {

            Cardinal cardinal = Cardinal.getInstance();

            cardinal.getLogger().info("[INSTANCES] List of commands:");
            cardinal.getLogger().info("instances - Show this message");
            cardinal.getLogger().info("instances list - List all instances");
            cardinal.getLogger().info("instances start <type> <amount> - Start a server instance");
            cardinal.getLogger().info("instances stop <name> - Stop a server instance");

        }
    }
}