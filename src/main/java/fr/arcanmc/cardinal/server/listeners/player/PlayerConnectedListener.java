package fr.arcanmc.cardinal.server.listeners.player;

import fr.arcanmc.cardinal.api.event.events.player.PlayerConnected;
import fr.arcanmc.cardinal.api.player.CardinalPlayer;
import fr.arcanmc.cardinal.core.event.EventListener;
import fr.arcanmc.cardinal.server.ServerService;

import java.util.HashMap;

public class PlayerConnectedListener extends EventListener<PlayerConnected> {
    @Override
    public String getName() {
        return "playerConnected";
    }

    @Override
    public void listen(PlayerConnected object) {
        ServerService.get().getPlayerManager().addPlayer(
                new CardinalPlayer(object.getUuid(), object.getName(), object.getServer(), object.getBungeeId(), new HashMap<>())
        );
    }
}
