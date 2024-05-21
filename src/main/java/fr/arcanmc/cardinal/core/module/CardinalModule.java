package fr.arcanmc.cardinal.core.module;


import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.file.FileConfiguration;

import java.io.File;

public class CardinalModule {

    private String name;
    private File dataFolder;
    private ModuleInfo info;
    private File pluginJar;

    protected final void setInfo(FileConfiguration file, File pluginJar) {
        this.info = new ModuleInfo(file);
        this.name = info.getName();
        this.dataFolder = new File(Cardinal.getInstance().getModuleDirectory(), name);
        this.pluginJar = pluginJar;
    }

    protected final File getPluginJar() {
        return pluginJar;
    }

    public void onLoad() {

    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public final String getName() {
        return name;
    }

    public final File getDataFolder() {
        return new File(dataFolder.getAbsolutePath());
    }

    public final ModuleInfo getInfo() {
        return info;
    }

    public final Cardinal getServer() {
        return Cardinal.getInstance();
    }

}
