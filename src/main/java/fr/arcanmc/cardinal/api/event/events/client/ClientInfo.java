package fr.arcanmc.cardinal.api.event.events.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The {@code ClientInfo} class represents information about a client.
 * It contains the client's ID, address and the number of instances.
 *
 * <p>
 * The class provides setter and getter methods for accessing and modifying
 * the client's information.
 * </p>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientInfo {

    private int id;

    private String address;

    private int instancesAmount;

}
