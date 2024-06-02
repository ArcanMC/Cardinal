package fr.arcanmc.cardinal.core.redis;

import fr.arcanmc.cardinal.Cardinal;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

public class RedisAccess {

    private static RedisAccess instance;
    private RedissonClient client;

    public RedisAccess(RedisCredentials credentials) {
        try {
            this.client = this.initRedisson(credentials);
            Cardinal.getInstance().getLogger().info("Now connect to Redis");
        } catch (org.redisson.client.RedisException e) {
            Cardinal.getInstance().getLogger().error("Wrong redis configuration..");
            Cardinal.getInstance().stopServer();
        }

        instance = this;
    }

    public RedissonClient initRedisson(RedisCredentials credentials) {
        Config config = new Config();
        config.setCodec(new JsonJacksonCodec());
        //config.setUseLinuxNativeEpoll(true);
        config.setThreads(4);
        config.setNettyThreads(4);
        config.useSingleServer()
                .setConnectionPoolSize(5)
                .setAddress(credentials.toURI())
                .setPassword(credentials.getPassword())
                .setDatabase(1)
                .setClientName(credentials.getClient());

        return Redisson.create(config);
    }

    public static void init() {
        Cardinal.getInstance().getLogger().info("Try to connect Redis");
        new RedisAccess(new RedisCredentials(
                Cardinal.getInstance().getServerProperties().getRedisHost(),
                Cardinal.getInstance().getServerProperties().getRedisPassword(),
                Cardinal.getInstance().getServerProperties().getRedisPort()));
    }

    public static void close() {
        Cardinal.getInstance().getLogger().info("Disconnecting from Redis");
        RedisAccess.get().getClient().shutdown();
    }

    public RedissonClient getClient() {
        return this.client;
    }

    public static RedisAccess get() {
        return instance;
    }
}
