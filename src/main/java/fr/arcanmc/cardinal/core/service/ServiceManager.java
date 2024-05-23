package fr.arcanmc.cardinal.core.service;

import fr.arcanmc.cardinal.api.service.Service;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class ServiceManager {

    private static ServiceManager instance;

    private final List<Service> modules;

    public ServiceManager() {
        instance = this;
        this.modules = new ArrayList<>();
    }

    public void register(Service module) {
        this.modules.add(module);
        module.onEnable();
        module.setEnabled(true);
    }

    public void unregister(Service module) {
        this.modules.remove(module);
        module.onDisable();
        module.setEnabled(false);
    }

    public void registerAll(Service... modules) {
        for (Service module : modules)
            register(module);
    }

    public void unloadAll() {
        Collection<Service> modules = getModules();
        modules.forEach(module -> module.setEnabled(false));
        modules.forEach(Service::onDisable);
    }

    public static ServiceManager get() {
        return instance;
    }

}
