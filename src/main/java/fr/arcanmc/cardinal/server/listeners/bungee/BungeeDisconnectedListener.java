package fr.arcanmc.cardinal.server.listeners.bungee;

import fr.arcanmc.cardinal.api.event.events.bungee.BungeeDisconnected;
import fr.arcanmc.cardinal.core.event.EventListener;
import fr.arcanmc.cardinal.server.ServerService;

public class BungeeDisconnectedListener extends EventListener<BungeeDisconnected> {
    @Override
    public String getName() {
        return "bungeeDisconnected";
    }

    @Override
    public void listen(BungeeDisconnected object) {
        ServerService.get().getBungeeManager().removeBungee(object.getId());
    }
}
