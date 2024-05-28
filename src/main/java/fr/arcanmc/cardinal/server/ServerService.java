package fr.arcanmc.cardinal.server;

import fr.arcanmc.cardinal.api.event.events.server.ForceStopClient;
import fr.arcanmc.cardinal.api.game.GameInstance;
import fr.arcanmc.cardinal.api.service.Service;
import fr.arcanmc.cardinal.client.event.ClientStoppedEvent;
import fr.arcanmc.cardinal.core.redis.RedisAccess;
import fr.arcanmc.cardinal.server.client.ClientManager;
import fr.arcanmc.cardinal.server.commands.HelpCommand;
import fr.arcanmc.cardinal.server.commands.client.ClientHelpCommand;
import fr.arcanmc.cardinal.server.commands.client.ClientInfoCommand;
import fr.arcanmc.cardinal.server.commands.client.ClientListCommand;
import fr.arcanmc.cardinal.server.commands.instances.InstanceHelpCommand;
import fr.arcanmc.cardinal.server.commands.instances.InstanceListCommand;
import fr.arcanmc.cardinal.server.commands.instances.InstanceStartCommand;
import fr.arcanmc.cardinal.server.commands.instances.InstanceStopCommand;
import fr.arcanmc.cardinal.server.event.ClientForceStopEvent;
import fr.arcanmc.cardinal.server.game.GameManager;
import fr.arcanmc.cardinal.server.listeners.*;
import lombok.Getter;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

@Getter
public class ServerService extends Service {

    private static ServerService instance;

    private ClientManager clientManager;
    private GameManager gameManager;

    @Override
    public String getName() {
        return "Server";
    }

    @Override
    public void onEnable() {
        instance = this;
        this.clientManager = new ClientManager();
        this.gameManager = new GameManager();

        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
        new ClientForceStopEvent(new ForceStopClient()).publish();

        RedissonClient redissonClient = RedisAccess.get().getClient();
        Iterable<String> keys = redissonClient.getKeys().getKeysByPattern("game:*");
        keys.forEach(key -> {
            RBucket<GameInstance> rBucket = redissonClient.getBucket(key);
            rBucket.delete();
        });
    }

    private void registerCommands() {
        registerCommand(new HelpCommand());

        registerCommand(new ClientHelpCommand());
        registerCommand(new ClientListCommand());
        registerCommand(new ClientInfoCommand());

        registerCommand(new InstanceHelpCommand());
        registerCommand(new InstanceStartCommand());
        registerCommand(new InstanceStopCommand());
        registerCommand(new InstanceListCommand());
    }

    private void registerListeners() {
        new ClientStartedListener();
        new ClientStopListener();
        new InstanceStartedListener();
        new InstanceStoppedListener();
        new StartInstanceListener();
        new StopInstanceListener();
    }

    public static ServerService get() {
        return instance;
    }
}
