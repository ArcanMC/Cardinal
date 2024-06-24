package fr.arcanmc.cardinal.server.game;

import fr.arcanmc.cardinal.api.client.Client;
import fr.arcanmc.cardinal.api.event.events.client.StartInstanceClient;
import fr.arcanmc.cardinal.api.event.events.server.StopInstance;
import fr.arcanmc.cardinal.api.game.GameInstance;
import fr.arcanmc.cardinal.core.redis.RedisAccess;
import fr.arcanmc.cardinal.server.ServerService;
import fr.arcanmc.cardinal.server.event.StartInstanceClientEvent;
import fr.arcanmc.cardinal.server.event.StopInstanceEvent;
import lombok.Getter;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.*;

public class GameManager {

    private final RedissonClient redisAccess;
    @Getter
    private final Set<String> games;

    public GameManager() {
        this.redisAccess = RedisAccess.get().getClient();
        this.games = new HashSet<>();
    }

    public void startGameInstance(String serverType, int amount, UUID host) {
        Client client = ServerService.get().getClientManager().getRandomClient();
        if (client == null) {
            return;
        }
        new StartInstanceClientEvent(new StartInstanceClient(serverType, client.getId(), amount, (host != null), host)).publish();
        fetchInstances();
    }

    public void startGameInstance(int clientId, String serverType, int amount, UUID host) {
        Client client = ServerService.get().getClientManager().getClient(clientId);
        if (client == null) {
            return;
        }
        new StartInstanceClientEvent(new StartInstanceClient(serverType, client.getId(), amount, (host != null), host)).publish();
        fetchInstances();
    }

    public void stopInstance(String name) {
        GameInstance gameInstance = getGameInstance(name);
        if (gameInstance != null) {
            new StopInstanceEvent(new StopInstance(name)).publish();
            fetchInstances();
            Client client = ServerService.get().getClientManager().getClient(gameInstance.getClientId());
            client.setInstanceAmount(client.getInstanceAmount() - 1);
        }
    }

    public void stopInstance(GameInstance gameInstance) {
        if (gameInstance != null) {
            new StopInstanceEvent(new StopInstance(gameInstance.getName())).publish();
            fetchInstances();
            Client client = ServerService.get().getClientManager().getClient(gameInstance.getClientId());
            client.setInstanceAmount(client.getInstanceAmount() - 1);
        }
    }

    public GameInstance getGameInstance(String name) {
        fetchInstances();
        RBucket<GameInstance> gameBucket = redisAccess.getBucket("gameinstance:" + name);
        return gameBucket.get();
    }

    public void updateGameInstance(GameInstance gameInstance) {
        RBucket<GameInstance> gameBucket = redisAccess.getBucket("gameinstance:" + gameInstance.getName());
        gameBucket.set(gameInstance);
    }

    public List<GameInstance> getGameInstances() {
        fetchInstances();
        List<GameInstance> gameInstances = new ArrayList<>();
        for (String game : games) {
            gameInstances.add(getGameInstance(game));
        }
        return gameInstances;
    }

    private void fetchInstances() {
        Iterable<String> keys = redisAccess.getKeys().getKeysByPattern("gameinstance:*");
        keys.forEach(key -> {
            RBucket<GameInstance> rBucket = redisAccess.getBucket(key);
            GameInstance serverInstance = rBucket.get();
            if (!isInstanceExist(serverInstance.getName())) {
                games.add(serverInstance.getName());
            }
        });
    }

    private boolean isInstanceExist(String serverName) {
        return games.contains(serverName);
    }

}
