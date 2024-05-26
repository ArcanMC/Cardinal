package fr.arcanmc.cardinal.client.listener;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.event.events.server.StopInstance;
import fr.arcanmc.cardinal.client.ClientService;
import fr.arcanmc.cardinal.core.event.EventListener;

public class StopInstanceListener extends EventListener<StopInstance> {
    @Override
    public String getName() {
        return "stopInstance";
    }

    @Override
    public void listen(StopInstance object) {
        if (ClientService.get().getGameManager().isMyInstance(object.getName())) {
            Cardinal.getInstance().getLogger().info("Stopping instance... [" + object.getName() + "]");
            ClientService.get().getGameManager().stopGameInstance(object.getName());
        }
    }
}
