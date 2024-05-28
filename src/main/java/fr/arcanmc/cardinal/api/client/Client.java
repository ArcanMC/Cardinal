package fr.arcanmc.cardinal.api.client;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Client {

    private final int id;
    private final String address;
    private final String name;
    @Setter
    private int instanceAmount;

    public Client(int id, String address, String name) {
        this.id = id;
        this.address = address;
        this.name = name;
    }

}
