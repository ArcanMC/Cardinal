package fr.arcanmc.cardinal.api.event.events.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Class representing a start instance.
 * The start instance can be of a specific type, have a specified amount, be hosted on a server, and be associated with a host using a UUID.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StartInstance {

    private String type;

    private int amount;

    private boolean hosted;

    private UUID host;

}
