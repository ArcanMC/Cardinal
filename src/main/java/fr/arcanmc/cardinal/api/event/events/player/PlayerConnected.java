package fr.arcanmc.cardinal.api.event.events.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerConnected {

    private UUID uuid;
    private String name;
    private int bungeeId;
    private String server;

}
