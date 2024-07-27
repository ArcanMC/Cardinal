package fr.arcanmc.cardinal.client.task;

import fr.arcanmc.cardinal.api.game.GameInstance;
import fr.arcanmc.cardinal.api.game.ServerStatus;
import fr.arcanmc.cardinal.api.scheduller.CardinalTask;
import fr.arcanmc.cardinal.client.ClientService;
import fr.arcanmc.cardinal.client.game.GameManager;

public class GameHealthChecker implements Runnable {

    GameManager gameManager = ClientService.get().getGameManager();
    ClientService clientService = ClientService.get();

    int instanceNotStartedSince = clientService.getConfig().get("health.autoStop.instanceNotStartedSince", Integer.class);
    int instanceNoPlayersSince = clientService.getConfig().get("health.autoStop.instanceNoPlayersSince", Integer.class);

    @Override
    public void run() {
        gameManager.getGameInstances().forEach(gameInstance -> {
            GameInstance game = gameManager.getInstanceByName(gameInstance);
            if (isUnstartedGameInstanceEligibleForStopping(game)) {
                stopGame(game);
            } else if (isRunningGameInstanceEligibleForStopping(game)) {
                stopGame(game);
            }
        });

    }

    private boolean isUnstartedGameInstanceEligibleForStopping(GameInstance game){
        return game.getStatus().ordinal() < ServerStatus.RUNNING.ordinal()
                && game.getCreatedAt() != null
                && game.getCreatedAt().getTime() + instanceNotStartedSince < System.currentTimeMillis();
    }

    private boolean isRunningGameInstanceEligibleForStopping(GameInstance game){
        return game.getStatus().ordinal() >= ServerStatus.RUNNING.ordinal()
                && game.getCreatedAt() != null
                && game.getCreatedAt().getTime() + instanceNoPlayersSince < System.currentTimeMillis()
                && game.getPlayers().isEmpty();
    }

    private void stopGame(GameInstance game){
        game.setStatus(ServerStatus.STOPPING);
        gameManager.stopGameInstance(game.getName());
    }
}
