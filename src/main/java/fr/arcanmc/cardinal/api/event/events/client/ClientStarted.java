package fr.arcanmc.cardinal.api.event.events.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The {@code ClientStarted} class represents a client who has already started their journey.
 * It contains information about the client's ID, name, and address.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientStarted {

    private int id;

    private String name;

    private String address;

}
