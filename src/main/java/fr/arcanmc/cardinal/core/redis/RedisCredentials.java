package fr.arcanmc.cardinal.core.redis;

import lombok.Getter;

@Getter
public class RedisCredentials {

    private final String ip;
    private final String password;
    private final int port;
    private final String client;

    public RedisCredentials(String ip, String password, int port) {
        this(ip, password, port, "Cardinal");
    }

    public RedisCredentials(String ip, String password, int port, String client) {
        this.ip = ip;
        this.password = password;
        this.port = port;
        this.client = client;
    }

    public String toURI() {
        return
                this.ip +
                ":" +
                this.port;
    }
}
