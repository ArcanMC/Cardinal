package fr.arcanmc.cardinal.client.task;

import fr.arcanmc.cardinal.api.game.GameInstance;
import fr.arcanmc.cardinal.api.game.ServerStatus;
import fr.arcanmc.cardinal.api.scheduller.CardinalTask;
import fr.arcanmc.cardinal.client.ClientService;
import fr.arcanmc.cardinal.client.game.GameManager;
import fr.arcanmc.cardinal.core.scheduler.CardinalScheduler;

public class GameHealthChecker implements CardinalTask {
    @Override
    public void run() {

        GameManager gameManager = ClientService.get().getGameManager();

        gameManager.getGameInstances().forEach(gameInstance -> {
            GameInstance game = gameManager.getInstanceByName(gameInstance);
            if (game.getStatus().ordinal() < ServerStatus.RUNNING.ordinal() && game.getCreatedAt() != null && game.getCreatedAt().getTime() + 120000 < System.currentTimeMillis()) {
                game.setStatus(ServerStatus.STOPPED);
            }
        });

    }
}
