package fr.arcanmc.cardinal.client.event;

import fr.arcanmc.cardinal.api.event.events.client.InstanceStopped;
import fr.arcanmc.cardinal.core.event.Event;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InstanceStoppedEvent extends Event<InstanceStopped> {

    private final InstanceStopped instanceStopped;

    @Override
    public String getName() {
        return "gameInstanceStopped";
    }

    @Override
    public InstanceStopped feed() {
        return instanceStopped;
    }
}
