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

    private final String logLevel;
    private final String type;
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

        this.logLevel = prop.getProperty("log_level") != null ? prop.getProperty("logLevel") : "INFO";

        this.type = prop.getProperty("type") != null ? prop.getProperty("type") : "SERVER";

        this.redisHost = prop.getProperty("redis_host") != null ? prop.getProperty("redis_host") : "localhost";
        this.redisPort = prop.getProperty("redis_port") != null ? Integer.parseInt(prop.getProperty("redis_port")) : 6379;
        this.redisPassword = prop.getProperty("redis_password") != null ? prop.getProperty("redis_password") : System.getenv("REDIS_PASSWORD");
    }

}
