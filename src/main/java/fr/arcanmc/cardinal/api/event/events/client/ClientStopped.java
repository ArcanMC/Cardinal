package fr.arcanmc.cardinal.api.event.events.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a stopped client.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientStopped {

    private String address;

}
