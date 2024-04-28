package fr.arcanmc.cardinal.module;


import fr.arcanmc.cardinal.Cardinal;

import java.io.File;

public class CardinalModule {

    private String name;
    private File dataFolder;
    private CardinalInfo info;
    private File pluginJar;

    protected final void setInfo(FileConfiguration file, File pluginJar) {
        this.info = new PluginInfo(file);
        this.name = info.getName();
        this.dataFolder = new File(Limbo.getInstance().getPluginFolder(), name);
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

    public final PluginInfo getInfo() {
        return info;
    }

    public final Cardinal getServer() {
        return Cardinal.getInstance();
    }

}
