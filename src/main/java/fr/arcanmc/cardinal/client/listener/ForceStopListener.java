package fr.arcanmc.cardinal.client.listener;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.event.events.server.ForceStopClient;
import fr.arcanmc.cardinal.client.ClientService;
import fr.arcanmc.cardinal.core.event.EventListener;

public class ForceStopListener extends EventListener<ForceStopClient> {


    @Override
    public String getName() {
        return "forceStopClient";
    }

    @Override
    public void listen(ForceStopClient object) {
        Cardinal.getInstance().getLogger().info("The server is stopping, the client will be closed.");
        ClientService.get().getGameManager().closeAll();
        Cardinal.getInstance().stopServer();
    }
}
