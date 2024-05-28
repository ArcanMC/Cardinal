package fr.arcanmc.cardinal.server.event;

import fr.arcanmc.cardinal.api.event.events.server.StopInstance;
import fr.arcanmc.cardinal.core.event.Event;

public class StopInstanceEvent extends Event<StopInstance> {

    private final StopInstance stopInstance;

    public StopInstanceEvent(StopInstance stopInstance) {
        this.stopInstance = stopInstance;
    }

    @Override
    public String getName() {
        return "stopInstance";
    }

    @Override
    public StopInstance feed() {
        return stopInstance;
    }
}
