package fr.arcanmc.cardinal.server.event;

import fr.arcanmc.cardinal.api.event.events.server.ForceStopClient;
import fr.arcanmc.cardinal.core.event.Event;

public class ClientForceStopEvent extends Event<ForceStopClient> {

    private final ForceStopClient forceStopClient;

    public ClientForceStopEvent(ForceStopClient forceStopClient) {
        this.forceStopClient = forceStopClient;
    }

    @Override
    public String getName() {
        return "forceStopClient";
    }

    @Override
    public ForceStopClient feed() {
        return forceStopClient;
    }
}
