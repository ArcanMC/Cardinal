package fr.arcanmc.cardinal.module;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.core.commands.DefaultCommands;
import fr.arcanmc.cardinal.file.FileConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ModuleManager {
    private static final String PLUGIN_DESCRIPTOR1 = "cardinal.yml";
    private static final String PLUGIN_DESCRIPTOR2 = "module.yml";
    private final Map<String, CardinalModule> modules;
    private final DefaultCommands defaultCommands;
    private final File moduleFolder;

    public ModuleManager(DefaultCommands defaultCommands, File moduleFolder) {
        this.defaultCommands = defaultCommands;
        this.moduleFolder = moduleFolder;
        this.modules = new LinkedHashMap<>();
    }

    protected void loadModules() {
        for (File file : Objects.requireNonNull(moduleFolder.listFiles())) {
            if (isJarFile(file)) {
                loadModuleFromJar(file);
            }
        }
    }

    private boolean isJarFile(File file) {
        return file.isFile() && file.getName().endsWith(".jar");
    }

    private void loadModuleFromJar(File file) {
        boolean hasValidDescriptor = false;

        try (ZipInputStream zip = new ZipInputStream(new FileInputStream(file))) {
            while (true) {
                ZipEntry entry = zip.getNextEntry();
                if (entry == null || hasValidDescriptor) {
                    break;
                }
                if (isPluginDescriptor(entry.getName())) {
                    loadModule(file, zip);
                    hasValidDescriptor = true;
                }
            }

        } catch (Exception e) {
            System.err.println("Unable to load module \"" + file.getName() + "\"");
            e.printStackTrace();
            return;
        }

        if (!hasValidDescriptor) {
            System.err.println("Jar file " + file.getName() + " has no valid descriptor!");
        }
    }

    private boolean isPluginDescriptor(String filename) {
        return filename.endsWith(PLUGIN_DESCRIPTOR1) || filename.endsWith(PLUGIN_DESCRIPTOR2);
    }

    private void loadModule(File sourceFile, ZipInputStream zip) throws Exception {
        FileConfiguration pluginConfig = new FileConfiguration(zip);
        String mainClass = pluginConfig.get("main", String.class);
        String moduleName = pluginConfig.get("name", String.class);

        if (modules.containsKey(moduleName)) {
            System.err.println("Ambiguous module name in " + sourceFile.getName() + " with the module \"" + modules.get(moduleName).getClass().getName() + "\"");
            return;
        }

        URLClassLoader loader = new URLClassLoader(new URL[]{sourceFile.toURI().toURL()}, Cardinal.getInstance().getClass().getClassLoader());
        Class<?> clazz = Class.forName(mainClass, true, loader);
        CardinalModule module = (CardinalModule) clazz.getDeclaredConstructor().newInstance();
        module.setInfo(pluginConfig, sourceFile);
        modules.put(module.getName(), module);
        module.onLoad();

        Cardinal.getInstance().getConsole().sendMessage("[Module] Loading: " + sourceFile.getName() + " " + module.getInfo().getVersion() + " by " + module.getInfo().getAuthor());
    }

    public List<CardinalModule> getModules() {
        return List.copyOf(modules.values());
    }

    public CardinalModule getModule(String name) {
        return modules.get(name);
    }

    public void executeDefaultCommands(String[] args) {
        try {
            defaultCommands.execute(args);
        } catch (Exception e) {
            System.err.println("Error while running default command \"" + args[0] + "\"");
            e.printStackTrace();
        }
    }
}
