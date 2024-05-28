package fr.arcanmc.cardinal.server.listeners;

import fr.arcanmc.cardinal.api.event.events.server.StopInstance;
import fr.arcanmc.cardinal.core.event.EventListener;
import fr.arcanmc.cardinal.server.ServerService;

public class StopInstanceListener extends EventListener<StopInstance> {
    @Override
    public String getName() {
        return "stopInstance";
    }

    @Override
    public void listen(StopInstance object) {
        ServerService.get().getGameManager().stopInstance(object.getName());
    }
}
