package fr.arcanmc.cardinal.api.event.events.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * This class represents an instance that has been started.
 * It provides the name of the instance.
 *
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InstanceStarted {

    private String name;

}
