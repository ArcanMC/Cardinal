package fr.arcanmc.cardinal.server.listeners.listener;

import fr.arcanmc.cardinal.api.event.events.server.StartInstance;
import fr.arcanmc.cardinal.core.event.EventListener;
import fr.arcanmc.cardinal.server.ServerService;

public class StartInstanceListener extends EventListener<StartInstance> {
    @Override
    public String getName() {
        return "startInstance";
    }

    @Override
    public void listen(StartInstance object) {
        ServerService.get().getGameManager().startGameInstance(object.getType(), object.getAmount(), object.getHost());
    }
}
