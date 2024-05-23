package fr.arcanmc.cardinal.client.event;

import fr.arcanmc.cardinal.api.event.events.client.ClientStarted;
import fr.arcanmc.cardinal.core.event.Event;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientStartedEvent extends Event<ClientStarted> {

    private final ClientStarted clientStarted;

    @Override
    public String getName() {
        return "clientStarted";
    }

    @Override
    public ClientStarted feed() {
        return this.clientStarted;
    }
}
