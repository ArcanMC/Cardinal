package fr.arcanmc.cardinal.api.service;

import fr.arcanmc.cardinal.api.command.CommandExecutor;
import fr.arcanmc.cardinal.core.commands.CommandManager;
import lombok.Getter;

/**
 * Service is an abstract class that represents a service module.
 * Subclasses of Service must implement the abstract methods {@link #getName()}, {@link #onEnable()}, and {@link #onDisable()}.
 */
@Getter
public abstract class Service {
    /**
     * Represents whether the service is enabled or not.
     */
    private boolean enabled;

    /**
     * Sets the enabled status of the service.
     *
     * @param enabled the new enabled status of the service
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Registers a command with the CommandManager.
     *
     * @param command The CommandExecutor instance to register.
     */
    public void registerCommand(CommandExecutor command) {
        CommandManager.get().registerCommands(command);
    }

    /**
     * Returns the name of the service.
     *
     * @return the name of the service.
     */
    public abstract String getName();

    /**
     * This method is invoked when the service is being enabled. It is an abstract method that must be implemented by subclasses of the Service class.
     *
     * Usage Example:
     *
     * ServiceManager serviceManager = ServiceManager.get();
     * Service module = new ExampleService();
     * serviceManager.register(module);
     *
     * @see Service
     * @see ServiceManager
     */
    public abstract void onEnable();

    /**
     * Represents the action to be taken when a service module is being disabled.
     */
    public abstract void onDisable();

}
