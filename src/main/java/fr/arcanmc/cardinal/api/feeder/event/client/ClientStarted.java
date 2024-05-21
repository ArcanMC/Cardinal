package fr.arcanmc.cardinal.api.feeder.event.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientStarted {

    private int id;

    private String name;

    private String address;

}
