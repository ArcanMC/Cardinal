package fr.arcanmc.cardinal.api.event.events.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The ClientHealth class represents the health information of a client.
 *
 * <p>
 * The class provides a set of methods to access and manipulate the client's health data.
 * </p>
 *
 * <p>
 * This class includes the following attributes:
 * </p>
 * <ul>
 * <li>id - The unique identifier of the client</li>
 * </ul>
 *
 * <p>
 * This class includes the following operations:
 * </p>
 * <ul>
 * <li>Getter and Setter methods for the id attribute</li>
 * <li>Constructor methods to create a new instance of ClientHealth</li>
 * </ul>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientHealth {

    private int id;

}
