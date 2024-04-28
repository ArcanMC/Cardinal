package fr.arcanmc.cardinal.core.commands;

import fr.arcanmc.cardinal.Cardinal;

public class DefaultCommands implements CommandExecutor {

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            return;
        }

        if (args[0].equalsIgnoreCase("version")) {
            Cardinal.getInstance().getConsole().sendMessage("Current version running " + Cardinal.VERSION);
            return;
        }

        if (args[0].equalsIgnoreCase("stop")) {
            Cardinal.getInstance().stopServer();
        }
    }
}
