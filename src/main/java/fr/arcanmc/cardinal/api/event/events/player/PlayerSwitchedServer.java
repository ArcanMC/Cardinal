package fr.arcanmc.cardinal.api.event.events.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerSwitchedServer {

    private UUID uuid;
    private String server;

}
