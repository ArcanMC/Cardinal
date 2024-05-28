package fr.arcanmc.cardinal.server.event;

import fr.arcanmc.cardinal.api.event.events.server.StartInstance;
import fr.arcanmc.cardinal.core.event.Event;

public class StartInstanceEvent extends Event<StartInstance> {

    private final StartInstance startInstance;

    public StartInstanceEvent(StartInstance startInstance) {
        this.startInstance = startInstance;
    }

    @Override
    public String getName() {
        return "startInstance";
    }

    @Override
    public StartInstance feed() {
        return startInstance;
    }
}
