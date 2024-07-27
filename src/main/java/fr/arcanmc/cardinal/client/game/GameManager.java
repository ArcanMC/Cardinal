package fr.arcanmc.cardinal.client.game;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.event.events.client.InstanceStopped;
import fr.arcanmc.cardinal.api.game.GameInstance;
import fr.arcanmc.cardinal.api.game.ServerStatus;
import fr.arcanmc.cardinal.api.template.Template;
import fr.arcanmc.cardinal.client.ClientService;
import fr.arcanmc.cardinal.client.event.InstanceStoppedEvent;
import fr.arcanmc.cardinal.client.template.TemplateManager;
import fr.arcanmc.cardinal.core.console.Logger;
import fr.arcanmc.cardinal.core.redis.RedisAccess;
import lombok.Getter;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.*;

public class GameManager {

    private final RedissonClient redissonClient;
    private static final String GAME_INSTANCE_IDF = "gameinstance:";
    @Getter
    private final Set<String> gameInstances;
    private final Logger logger;
    private final TemplateManager templateManager;

    public GameManager() {
        this.redissonClient = RedisAccess.get().getClient();
        this.logger = Cardinal.getInstance().getLogger();
        this.gameInstances = new HashSet<>();
        this.templateManager = ClientService.get().getTemplateManager();
    }

    public void startGameInstance(String template, UUID host) {
        int clientId = ClientService.get().getMyId();
        String ip = ClientService.get().getMyIp();

        if (!templateManager.templateExists(template)) {
            logger.error("Template " + template + " does not exist");
            return;
        }
        if (!templateManager.templateHasImage(template)) {
            logger.error("Template " + template + " does not have an image");
            return;
        }
        Template templateData = templateManager.getTemplate(template);
        GameInstance gameInstance = new GameInstance(clientId, template, generateGameId(), ip, getAvailablePort(), host);
        try {
            Process process = Runtime.getRuntime().exec("docker run" +
                    " -p" + gameInstance.getPort() + ":25565" +
                    " --name " + gameInstance.getName() +
                    " -e SERVERNAME=" + gameInstance.getName() +
                    " -e SERVERTYPE=" + gameInstance.getTemplateName() +
                    " -d " + templateData.getImageId());
            BufferedReader bufferedReaderObj = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String sLine = "";
            StringBuilder output = new StringBuilder();
            while ((sLine = bufferedReaderObj.readLine()) != null) {
                output.append(sLine);
            }
        } catch (Exception e) {
            logger.error("Could not start game instance for template " + template);
            return;
        }
        gameInstance.setStatus(ServerStatus.STARTING);

        final String key = GAME_INSTANCE_IDF + gameInstance.getName();
        final RBucket<GameInstance> bucket = redissonClient.getBucket(key);

        bucket.set(gameInstance);

        gameInstances.add(gameInstance.getName());
    }

    public void stopGameInstance(String serverName) {
        if (getInstanceByName(serverName) == null) {
            logger.error("Game instance " + serverName + " does not exist");
            return;
        }
        final String key = GAME_INSTANCE_IDF + serverName;
        final RBucket<GameInstance> gameBucket = redissonClient.getBucket(key);
        this.gameInstances.remove(serverName);
        try {
            Runtime.getRuntime().exec("docker rm -f " + serverName);
        } catch (IOException e) {
            logger.error("Could not stop game instance " + serverName);
        }
        new InstanceStoppedEvent(new InstanceStopped(serverName)).publish();
        gameBucket.delete();
    }

    private void executeDockerRunCommand(GameInstance gameInstance, Template templateData) throws IOException{
        Process process = Runtime.getRuntime().exec("docker run" +
                " -p" + gameInstance.getPort() + ":25565" +
                " --name " + gameInstance.getName() +
                " -e SERVERNAME=" + gameInstance.getName() +
                " -e SERVERTYPE=" + gameInstance.getTemplateName() +
                " -d " + templateData.getImageId());

        try(BufferedReader bufferedReaderObj = new BufferedReader(new InputStreamReader(process.getInputStream()))){
            String sLine;
            StringBuilder output = new StringBuilder();

            while ((sLine = bufferedReaderObj.readLine()) != null) {
                output.append(sLine);
            }
        }
    }

    public void closeAll() {
        this.gameInstances.forEach(this::stopGameInstance);
    }

    public GameInstance getInstanceByName(String serverName) {
        return getInstanceFromRedis(serverName);
    }

    public boolean isMyInstance(String serverName) {
        return this.gameInstances.contains(serverName);
    }

    private GameInstance getInstanceFromRedis(String serverName) {
        final RedissonClient redissonClient = RedisAccess.get().getClient();
        final String key = GAME_INSTANCE_IDF + serverName;
        final RBucket<GameInstance> accountRBucket = redissonClient.getBucket(key);

        return accountRBucket.get();
    }

    private String generateGameId() {
        char[] letters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] numbers = "1234567890".toCharArray();

        Random random = new Random();
        StringBuilder gameId = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            gameId.append(letters[random.nextInt(letters.length)]);
        }
        for (int i = 0; i < 3; i++) {
            gameId.append(numbers[random.nextInt(numbers.length)]);
        }
        return gameId.toString();
    }

    private int getAvailablePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (Exception e) {
            logger.error("Could not find available port");
        }
        return 0;
    }

}
