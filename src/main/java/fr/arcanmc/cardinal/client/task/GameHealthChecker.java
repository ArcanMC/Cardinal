package fr.arcanmc.cardinal.client.task;

import fr.arcanmc.cardinal.api.game.GameInstance;
import fr.arcanmc.cardinal.api.game.ServerStatus;
import fr.arcanmc.cardinal.api.scheduller.CardinalTask;
import fr.arcanmc.cardinal.client.ClientService;
import fr.arcanmc.cardinal.client.game.GameManager;

public class GameHealthChecker implements CardinalTask {

    GameManager gameManager = ClientService.get().getGameManager();
    ClientService clientService = ClientService.get();

    int instanceNotStartedSince = clientService.getConfig().get("health.autoStop.instanceNotStartedSince", Integer.class);
    int instanceNoPlayersSince = clientService.getConfig().get("health.autoStop.instanceNoPlayersSince", Integer.class);

    @Override
    public void run() {
        gameManager.getGameInstances().forEach(gameInstance -> {
            GameInstance game = gameManager.getInstanceByName(gameInstance);
            if (game.getStatus().ordinal() < ServerStatus.RUNNING.ordinal() && game.getCreatedAt() != null && game.getCreatedAt().getTime() + instanceNotStartedSince < System.currentTimeMillis()) {
                game.setStatus(ServerStatus.STOPPING);
                gameManager.stopGameInstance(game.getName());
            } else if (game.getStatus().ordinal() >= ServerStatus.RUNNING.ordinal() && game.getCreatedAt() != null && game.getCreatedAt().getTime() + instanceNoPlayersSince < System.currentTimeMillis() && game.getPlayers().isEmpty()) {
                game.setStatus(ServerStatus.STOPPING);
                gameManager.stopGameInstance(game.getName());
            }
        });

    }
}
