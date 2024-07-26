package fr.arcanmc.cardinal.server.listeners.listener;

import fr.arcanmc.cardinal.api.event.events.client.InstanceStopped;
import fr.arcanmc.cardinal.api.event.events.server.StopInstance;
import fr.arcanmc.cardinal.core.event.EventListener;
import fr.arcanmc.cardinal.server.ServerService;

public class InstanceStoppedListener extends EventListener<InstanceStopped> {
    @Override
    public String getName() {
        return "gameInstanceStopped";
    }

    @Override
    public void listen(InstanceStopped object) {
        ServerService.get().getGameManager().getGames().remove(object.getName());

    }
}
