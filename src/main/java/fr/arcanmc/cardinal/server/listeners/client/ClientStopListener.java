package fr.arcanmc.cardinal.server.listeners.client;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.client.Client;
import fr.arcanmc.cardinal.api.event.events.client.ClientStopped;
import fr.arcanmc.cardinal.core.event.EventListener;
import fr.arcanmc.cardinal.server.ServerService;
import fr.arcanmc.cardinal.server.client.ClientManager;

public class ClientStopListener extends EventListener<ClientStopped> {
    @Override
    public String getName() {
        return "clientStopped";
    }

    @Override
    public void listen(ClientStopped object) {
        Cardinal.getInstance().getLogger().info("Client disconnected: " + object.getAddress());
        ClientManager clientManager = ServerService.get().getClientManager();
        Client client = clientManager.getClientByAddress(object.getAddress());
        clientManager.removeClient(client);
    }
}
