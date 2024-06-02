package fr.arcanmc.cardinal.server.listeners.player;

import fr.arcanmc.cardinal.api.event.events.player.PlayerSwitchedServer;
import fr.arcanmc.cardinal.api.game.GameInstance;
import fr.arcanmc.cardinal.api.player.CardinalPlayer;
import fr.arcanmc.cardinal.core.event.EventListener;
import fr.arcanmc.cardinal.server.ServerService;
import fr.arcanmc.cardinal.server.game.GameManager;

public class PlayerSwitchInstanceListener extends EventListener<PlayerSwitchedServer> {
    @Override
    public String getName() {
        return "playerSwitchedServer";
    }

    @Override
    public void listen(PlayerSwitchedServer object) {
        CardinalPlayer player = ServerService.get().getPlayerManager().getPlayer(object.getUuid());
        player.setCurrentServer(object.getServer());
        GameManager gameManager = ServerService.get().getGameManager();
        GameInstance gameInstance = gameManager.getGameInstance(object.getServer());
        if (gameInstance != null) {
            gameInstance.addPlayer(player);
            gameManager.updateGameInstance(gameInstance);
        }
    }
}
