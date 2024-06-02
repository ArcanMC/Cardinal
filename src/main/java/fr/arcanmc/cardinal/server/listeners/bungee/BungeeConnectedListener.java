package fr.arcanmc.cardinal.server.listeners.bungee;

import fr.arcanmc.cardinal.api.bungee.Bungee;
import fr.arcanmc.cardinal.api.event.events.bungee.BungeeConnected;
import fr.arcanmc.cardinal.core.event.EventListener;
import fr.arcanmc.cardinal.server.ServerService;

public class BungeeConnectedListener extends EventListener<BungeeConnected> {
    @Override
    public String getName() {
        return "bungeeConnected";
    }

    @Override
    public void listen(BungeeConnected object) {
        ServerService.get().getBungeeManager().addBungee(new Bungee(object.getId(), object.getAddress(), object.getPort()));
    }
}
