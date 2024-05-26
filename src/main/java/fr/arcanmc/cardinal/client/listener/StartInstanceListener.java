package fr.arcanmc.cardinal.client.listener;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.event.events.server.StartInstance;
import fr.arcanmc.cardinal.client.ClientService;
import fr.arcanmc.cardinal.core.event.EventListener;

public class StartInstanceListener extends EventListener<StartInstance> {
    @Override
    public String getName() {
        return "startInstance";
    }

    @Override
    public void listen(StartInstance object) {
        if (object.getClientID() == ClientService.get().getMyId()) {
            Cardinal.getInstance().getLogger().info("Starting instance... [" + object.getType() + "/" + object.getAmount() + "]");
            for (int i = 0; i < object.getAmount(); i++) {
                ClientService.get().getGameManager().startGameInstance(object.getType(), object.getHost());
            }
        }
    }
}
