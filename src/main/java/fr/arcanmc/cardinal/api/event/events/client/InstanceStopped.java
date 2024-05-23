package fr.arcanmc.cardinal.api.event.events.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents an instance that has stopped.
 * <p>
 * Instances of this class contain information about the name of the stopped instance.
 * </p>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InstanceStopped {

    private String name;

}
