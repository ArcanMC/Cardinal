package fr.arcanmc.cardinal;

import fr.arcanmc.cardinal.client.ClientService;
import fr.arcanmc.cardinal.core.commands.CommandManager;
import fr.arcanmc.cardinal.core.commands.DefaultCommands;
import fr.arcanmc.cardinal.core.console.Console;
import fr.arcanmc.cardinal.core.console.Logger;
import fr.arcanmc.cardinal.core.redis.RedisAccess;
import fr.arcanmc.cardinal.core.scheduler.CardinalScheduler;
import fr.arcanmc.cardinal.core.scheduler.Tick;
import fr.arcanmc.cardinal.file.ServerProperties;
import fr.arcanmc.cardinal.core.module.CardinalModule;
import fr.arcanmc.cardinal.core.module.ModuleManager;
import fr.arcanmc.cardinal.core.service.ServiceManager;
import fr.arcanmc.cardinal.server.ServerService;
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
    private final Logger logger;

    @Getter
    private final CardinalScheduler cardinalScheduler;

    @Getter
    private final ServerProperties serverProperties;

    @Getter
    private final CommandManager commandManager;

    @Getter
    private final File moduleDirectory;

    @Getter
    private final ModuleManager moduleManager;

    @Getter
    private final ServiceManager serviceManager;

    public Cardinal() {
        instance = this;

        console = initializeConsole();
        serverProperties = initializeServerProperties();

        try {
            logger = new Logger(console.getIn(), console.getOut(), console.getErr());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.commandManager = new CommandManager(new DefaultCommands());

        addShutdownHook();

        String mode = serverProperties.getType();

        this.logger.info("Starting Cardinal in mode: " + mode);

        cardinalScheduler = new CardinalScheduler();
        tick = new Tick(this);

        this.serviceManager = new ServiceManager();

        RedisAccess.init();

        if (mode.equalsIgnoreCase("SERVER")) {
            this.logger.info("Loading Services");
            serviceManager.register(new ServerService());
        } else if (mode.equalsIgnoreCase("CLIENT")) {
            this.logger.info("Loading Services");
            serviceManager.register(new ClientService());
        } else {
            this.logger.error("Invalid mode in server.properties");
            terminate();
        }

        this.logger.info("Loading Modules");
        moduleDirectory = new File("modules");
        moduleDirectory.mkdirs();
        moduleManager = new ModuleManager(new DefaultCommands(), moduleDirectory);
        loadModulesForManager();
        enableAllModules();

        this.logger.info("Cardinal is now up!");
        this.running.set(true);
        console.run();
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
                this.logger.error("Failed to copy server.properties file");
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
            this.logger.error("Failed to load modules");
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
        this.serviceManager.unloadAll();
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
            this.logger.error("An error occurred while executing command: " + String.join(" ", args));
        }
    }
}
