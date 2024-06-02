package fr.arcanmc.cardinal.server.listeners.player;

import fr.arcanmc.cardinal.api.event.events.player.PlayerDisconnected;
import fr.arcanmc.cardinal.core.event.EventListener;
import fr.arcanmc.cardinal.server.ServerService;

public class PlayerDisconnectedListener extends EventListener<PlayerDisconnected> {
    @Override
    public String getName() {
        return "playerDisconnected";
    }

    @Override
    public void listen(PlayerDisconnected object) {
        ServerService.get().getPlayerManager().removePlayer(object.getUuid());
    }
}
