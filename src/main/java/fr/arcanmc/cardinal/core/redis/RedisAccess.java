package fr.arcanmc.cardinal.core.redis;

import fr.arcanmc.cardinal.Cardinal;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

public class RedisAccess {

    private static final int REDIS_THREADS = 4;
    private static final int CONNECTION_POOL_SIZE = 10;
    private static final int DATABASE_NUMBER = 1;
    private static final int CONNECTION_MINIMUM_IDLE_SIZE = 1;
    private static RedisAccess singletonInstance;
    private RedissonClient redissonClient;

    public RedisAccess(RedisCredentials credentials) {
        try {
            this.redissonClient = initializeRedisson(credentials);
            System.out.println("Now connect to Redis");
        } catch (org.redisson.client.RedisException e) {
            System.out.println("Wrong redis configuration..");
            Cardinal.getInstance().stopServer();
        }

        singletonInstance = this;
    }

    public RedissonClient initializeRedisson(RedisCredentials credentials) {
        Config config = new Config();
        setJsonCodec(config);
        config.setThreads(REDIS_THREADS);
        config.setNettyThreads(REDIS_THREADS);
        config.useSingleServer()
                .setConnectionPoolSize(CONNECTION_POOL_SIZE)
                .setAddress(credentials.toURI())
                .setPassword(credentials.getPassword())
                .setDatabase(DATABASE_NUMBER)
                .setConnectionMinimumIdleSize(CONNECTION_MINIMUM_IDLE_SIZE)
                .setClientName(credentials.getClient());
        return createRedissonClient(config);
    }

    public static void init() {
        Cardinal cardinal = Cardinal.getInstance();
        new RedisAccess(new RedisCredentials(
                cardinal.getServerProperties().getRedisHost(),
                cardinal.getServerProperties().getRedisPassword(),
                cardinal.getServerProperties().getRedisPort()));
    }

    public static void close() {
        System.out.println("Closing Redis connection..");
        RedisAccess.get().getClient().shutdown();
    }

    public RedissonClient getClient() {
        return this.redissonClient;
    }

    public static RedisAccess get() {
        return singletonInstance;
    }

    private void setJsonCodec(Config config) {
        config.setCodec(new JsonJacksonCodec());
    }

    private RedissonClient createRedissonClient(Config config) {
        return Redisson.create(config);
    }
}
