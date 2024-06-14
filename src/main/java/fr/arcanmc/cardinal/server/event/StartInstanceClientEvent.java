package fr.arcanmc.cardinal.server.event;

import fr.arcanmc.cardinal.api.event.events.client.StartInstanceClient;
import fr.arcanmc.cardinal.core.event.Event;

public class StartInstanceClientEvent extends Event<StartInstanceClient> {

    private final StartInstanceClient startInstance;

    public StartInstanceClientEvent(StartInstanceClient startInstance) {
        this.startInstance = startInstance;
    }

    @Override
    public String getName() {
        return "startInstanceClient";
    }

    @Override
    public StartInstanceClient feed() {
        return startInstance;
    }

}
