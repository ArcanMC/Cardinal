package fr.arcanmc.cardinal;

import fr.arcanmc.cardinal.core.commands.CommandManager;
import fr.arcanmc.cardinal.core.commands.DefaultCommands;
import fr.arcanmc.cardinal.core.console.Console;
import fr.arcanmc.cardinal.core.redis.RedisAccess;
import fr.arcanmc.cardinal.core.scheduler.CardinalScheduler;
import fr.arcanmc.cardinal.core.scheduler.Tick;
import fr.arcanmc.cardinal.file.ServerProperties;
import fr.arcanmc.cardinal.module.CardinalModule;
import fr.arcanmc.cardinal.module.ModuleManager;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicBoolean;

public class Cardinal {
    public static final String VERSION = "0.0.1";

    @Getter
    private static Cardinal instance;

    @Getter
    private final AtomicBoolean running = new AtomicBoolean(false);

    @Getter
    private final Console console;

    private final Tick tick;

    @Getter
    private final CardinalScheduler cardinalScheduler;

    @Getter
    private final ServerProperties serverProperties;

    private CommandManager commandManager;

    @Getter
    private final File moduleDirectory;

    @Getter
    private final ModuleManager moduleManager;

    public Cardinal() {
        instance = this;
        System.out.println("Init console");
        console = initializeConsole();
        console.logs.println("Init shutdown hook");
        addShutdownHook();
        console.logs.println("Init server properties");
        serverProperties = initializeServerProperties();
        console.logs.println("Init redis");
        RedisAccess.init();
        console.logs.println("Init scheduler");
        cardinalScheduler = new CardinalScheduler();
        tick = new Tick(this);
        console.logs.println("Init modules");
        moduleDirectory = new File("modules");
        moduleDirectory.mkdirs();
        moduleManager = new ModuleManager(new DefaultCommands(), moduleDirectory);
        loadModulesForManager();
        enableAllModules();
    }

    public CommandManager getCommandManager() {
        if (commandManager == null) {
            commandManager = new CommandManager(new DefaultCommands());
        }
        return commandManager;
    }

    private Console initializeConsole() {
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

    private ServerProperties initializeServerProperties() {
        File serverPropertiesFile = new File("server.properties");
        copyPropertiesFileIfNotExists(serverPropertiesFile);
        try {
            return new ServerProperties(serverPropertiesFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void copyPropertiesFileIfNotExists(File serverPropertiesFile) {
        if (!serverPropertiesFile.exists()) {
            try (InputStream in = getClass().getClassLoader().getResourceAsStream("server.properties")) {
                if (in != null)
                    Files.copy(in, serverPropertiesFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadModulesForManager() {
        try {
            Method loadModulesMethod = ModuleManager.class.getDeclaredMethod("loadModules");
            loadModulesMethod.setAccessible(true);
            loadModulesMethod.invoke(moduleManager);
            loadModulesMethod.setAccessible(false);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    private void enableAllModules() {
        for (CardinalModule module : getModuleManager().getModules()) {
            enableModule(module);
        }
    }

    private void enableModule(CardinalModule module) {
        try {
            console.sendMessage("Enabling plugin " + module.getName() + " " + module.getInfo().getVersion());
            module.onEnable();
        } catch (Throwable e) {
            new RuntimeException("Error while enabling " + module.getName() + " " + module.getInfo().getVersion(), e).printStackTrace();
        }
    }

    protected void terminate() {
        running.set(false);
        RedisAccess.close();
        for (CardinalModule plugin : Cardinal.getInstance().getModuleManager().getModules()) {
            try {
                console.sendMessage("Disabling module " + plugin.getName() + " " + plugin.getInfo().getVersion());
                plugin.onDisable();
            } catch (Throwable e) {
                new RuntimeException("Error while disabling " + plugin.getName() + " " + plugin.getInfo().getVersion(), e).printStackTrace();
            }
        }
        tick.waitAndKillThreads(5000);
        console.sendMessage("Cardinal closed");
        console.logs.close();
    }

    public Tick getHeartBeat() {
        return tick;
    }

    public boolean isRunning() {
        return running.get();
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
}
