package fr.arcanmc.cardinal.client.listener;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.event.events.client.StartInstanceClient;
import fr.arcanmc.cardinal.client.ClientService;
import fr.arcanmc.cardinal.core.event.EventListener;

public class StartInstanceListener extends EventListener<StartInstanceClient> {
    @Override
    public String getName() {
        return "startInstanceClient";
    }

    @Override
    public void listen(StartInstanceClient object) {
        if (object.getClientID() == ClientService.get().getMyId()) {
            Cardinal.getInstance().getLogger().info("Starting instance... [" + object.getType() + "/" + object.getAmount() + "]");
            for (int i = 0; i < object.getAmount(); i++) {
                try {
                    ClientService.get().getGameManager().startGameInstance(object.getType(), object.getHost());
                } catch (RuntimeException e) {
                    Cardinal.getInstance().getLogger().error("Failed to start game instance!", e);
                    throw e;
                }
            }
        }
    }
}
