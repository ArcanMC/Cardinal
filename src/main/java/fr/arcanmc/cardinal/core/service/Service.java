package fr.arcanmc.cardinal.core.service;

import fr.arcanmc.cardinal.api.command.CommandExecutor;
import fr.arcanmc.cardinal.core.commands.CommandManager;
import lombok.Getter;

@Getter
public abstract class Service {
    private boolean enabled;

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void registerCommand(CommandExecutor command) {
        CommandManager.get().registerCommands(command);
    }

    public abstract String getName();

    public abstract void onEnable();

    public abstract void onDisable();

}
