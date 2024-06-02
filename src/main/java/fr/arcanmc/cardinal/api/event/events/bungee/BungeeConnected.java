package fr.arcanmc.cardinal.api.event.events.bungee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BungeeConnected {

    private int id;
    private String address;
    private int port;

}
