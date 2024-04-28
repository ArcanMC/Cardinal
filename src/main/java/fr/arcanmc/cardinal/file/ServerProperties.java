package fr.arcanmc.cardinal.file;

import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

@Getter
public class ServerProperties {

    private final File file;

    private final int port;
    private final String logLevel;
    private final String awsAccessKeyId,
            awsSecretAccessKey,
            awsRegion,
            awsBucket;
    private final String redisHost, redisPassword;
    private final int redisPort;

    public ServerProperties(File file) throws IOException {
        this.file = file;

        Properties def = new Properties();
        InputStreamReader dfReader = new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("server.properties")), StandardCharsets.UTF_8);
        def.load(dfReader);
        dfReader.close();

        Properties prop = new Properties();
        InputStreamReader stream = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
        prop.load(stream);
        stream.close();

        for (Map.Entry<Object, Object> entry : def.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            prop.putIfAbsent(key, value);
        }

        this.port = prop.getProperty("port") != null ? Integer.parseInt(prop.getProperty("port")) : 8080;
        this.logLevel = prop.getProperty("log_level") != null ? prop.getProperty("logLevel") : "INFO";

        this.awsAccessKeyId = prop.getProperty("aws_access_key_id");
        this.awsSecretAccessKey = prop.getProperty("aws_secret_access_key");
        this.awsRegion = prop.getProperty("aws_region");
        this.awsBucket = prop.getProperty("aws_bucket");

        this.redisHost = prop.getProperty("redis_host");
        this.redisPort = prop.getProperty("redis_port") != null ? Integer.parseInt(prop.getProperty("redisPort")) : 6379;
        this.redisPassword = prop.getProperty("redis_password");
    }

}
