package fr.arcanmc.cardinal.server.listeners.listener;

import fr.arcanmc.cardinal.api.client.Client;
import fr.arcanmc.cardinal.api.event.events.client.InstanceStarted;
import fr.arcanmc.cardinal.api.game.GameInstance;
import fr.arcanmc.cardinal.core.event.EventListener;
import fr.arcanmc.cardinal.server.ServerService;

public class InstanceStartedListener extends EventListener<InstanceStarted> {
    @Override
    public String getName() {
        return "gameInstanceStarted";
    }

    @Override
    public void listen(InstanceStarted object) {
        ServerService serverService = ServerService.get();
        GameInstance gameInstance = serverService.getGameManager().getGameInstance(object.getName());
        if (gameInstance != null) {
            Client client = serverService.getClientManager().getClient(gameInstance.getClientId());
            client.setInstanceAmount(client.getInstanceAmount() + 1);
            serverService.getGameManager().getGameInstances().add(gameInstance);
        }

    }
}
