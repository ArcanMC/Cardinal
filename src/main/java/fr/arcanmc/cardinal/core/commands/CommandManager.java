package fr.arcanmc.cardinal.core.commands;

import fr.arcanmc.cardinal.core.commands.DefaultCommands;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private static CommandManager instance;
    private final DefaultCommands defaultExecutor;
    private final List<Executor> executors;

    public CommandManager(DefaultCommands defaultExecutor) {
        instance = this;
        this.defaultExecutor = defaultExecutor;
        this.executors = new ArrayList<>();
    }

    public void fireExecutors(String[] args) {
        executeCommand(args, this.defaultExecutor, "Error while running default command \"%s\"".formatted(args[0]));
        for (Executor entry : executors) {
            executeCommand(args, entry.executor, "Error while passing command %s".formatted(args[0]));
        }
    }

    private void executeCommand(String[] args, CommandExecutor executor, String errorMessage) {
        try {
            executor.execute(args);
        } catch (Exception e) {
            System.err.println(errorMessage);
            e.printStackTrace();
        }
    }

    public void registerCommands(CommandExecutor executor) {
        executors.add(new Executor(executor));
    }

    protected static class Executor {
        public final CommandExecutor executor;

        public Executor(CommandExecutor executor) {
            this.executor = executor;
        }
    }

    public static CommandManager get() {
        return instance;
    }
}
