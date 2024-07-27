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
        ClientService clientService = ClientService.get();
        if (object.getClientID() == clientService.getMyId()) {
            String instanceStartMessage = String.format("Starting instance... [%s/%d]", object.getType(),
                    object.getAmount());
            Cardinal.getInstance().getLogger().info(instanceStartMessage);
            for (int i = 0; i < object.getAmount(); i++) {
                try {
                    clientService.getGameManager().startGameInstance(object.getType(), object.getHost());
                } catch (RuntimeException e) {
                    String errorMessage = "Failed to start game instance!";
                    Cardinal.getInstance().getLogger().error(errorMessage, e);
                    throw e;
                }
            }
        }
    }
}
