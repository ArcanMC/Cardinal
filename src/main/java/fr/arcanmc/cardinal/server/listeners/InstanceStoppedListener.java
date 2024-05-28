package fr.arcanmc.cardinal.server.listeners;

import fr.arcanmc.cardinal.api.client.Client;
import fr.arcanmc.cardinal.api.event.events.client.InstanceStarted;
import fr.arcanmc.cardinal.api.event.events.server.StopInstance;
import fr.arcanmc.cardinal.api.game.GameInstance;
import fr.arcanmc.cardinal.core.event.EventListener;
import fr.arcanmc.cardinal.server.ServerService;

public class InstanceStoppedListener extends EventListener<StopInstance> {
    @Override
    public String getName() {
        return "gameInstanceStopped";
    }

    @Override
    public void listen(StopInstance object) {
        ServerService.get().getGameManager().getGames().remove(object.getName());

    }
}