package fr.arcanmc.cardinal.api.event.events.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StartInstanceClient {

    private String type;

    private int clientID;

    private int amount;

    private boolean hosted;

    private UUID host;

}
