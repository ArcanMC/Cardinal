package fr.arcanmc.cardinal.client.event;

import fr.arcanmc.cardinal.api.event.events.client.ClientStopped;
import fr.arcanmc.cardinal.core.event.Event;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientStoppedEvent extends Event<ClientStopped> {

    private final ClientStopped clientStopped;

    @Override
    public String getName() {
        return "clientStopped";
    }

    @Override
    public ClientStopped feed() {
        return this.clientStopped;
    }
}
