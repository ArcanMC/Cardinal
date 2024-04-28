package fr.arcanmc.cardinal;

import fr.arcanmc.cardinal.core.commands.CommandManager;
import fr.arcanmc.cardinal.core.commands.DefaultCommands;
import fr.arcanmc.cardinal.core.console.Console;
import fr.arcanmc.cardinal.core.redis.RedisAccess;
import fr.arcanmc.cardinal.core.scheduler.CardinalScheduler;
import fr.arcanmc.cardinal.core.scheduler.Tick;
import fr.arcanmc.cardinal.file.ServerProperties;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicBoolean;

public class Cardinal {

    public static final String VERSION = "0.0.1";

    @Getter
    private static Cardinal instance;
    @Getter
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    @Getter
    private final Console console;
    private final Tick tick;
    @Getter
    private final CardinalScheduler cardinalScheduler;
    @Getter
    private final ServerProperties serverProperties;
    private CommandManager commandManager;

    public Cardinal() {
        instance = this;
        console = initConsole();
        addShutdownHook();
        serverProperties = initServerProperties();
        RedisAccess.init();
        cardinalScheduler = new CardinalScheduler();
        tick = new Tick(this);
    }

    public CommandManager getCommandManager() {
        if (commandManager == null) {
            commandManager = new CommandManager(new DefaultCommands());
        }
        return commandManager;
    }

    private Console initConsole() {
        try {
            return new Console(System.in, System.out, System.err);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Cardinal.getInstance().terminate();
        }));
    }

    private ServerProperties initServerProperties() {
        File serverPropertiesFile = new File("server.properties");
        if (!serverPropertiesFile.exists()) {
            try (InputStream in = getClass().getClassLoader().getResourceAsStream("server.properties")) {
                if (in != null) Files.copy(in, serverPropertiesFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            return new ServerProperties(serverPropertiesFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void terminate() {
        isRunning.set(false);

        RedisAccess.close();

        tick.waitAndKillThreads(5000);
        console.sendMessage("Kraken closed");
        console.logs.close();
    }

    public void stopServer() {
        System.exit(0);
    }

    public void dispatchCommand(String... args) {
        try {
            CommandManager.get().fireExecutors(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Tick getHeartBeat() {
        return tick;
    }

    public CardinalScheduler getScheduler() {
        return cardinalScheduler;
    }

    public boolean isRunning() {
        return isRunning.get();
    }

}
