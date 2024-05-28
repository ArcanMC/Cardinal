package fr.arcanmc.cardinal.server.listeners;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.client.Client;
import fr.arcanmc.cardinal.api.event.events.client.ClientStarted;
import fr.arcanmc.cardinal.core.event.EventListener;
import fr.arcanmc.cardinal.server.ServerService;
import fr.arcanmc.cardinal.server.client.ClientManager;

public class ClientStartedListener extends EventListener<ClientStarted> {

    @Override
    public String getName() {
        return "clientStarted";
    }

    @Override
    public void listen(ClientStarted object) {
        ClientManager clientManager = ServerService.get().getClientManager();
        if (clientManager.getClient(object.getId()) != null) {
            Cardinal.getInstance().getLogger().error("Client " + object.getId() + " already exists.");
            return;
        }
        Cardinal.getInstance().getLogger().info("New client connected: " + object.getAddress() + ":" + object.getId() + ":" + object.getName());
        clientManager.addClient(new Client(object.getId(), object.getAddress(), object.getName()));
    }
}
