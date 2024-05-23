package fr.arcanmc.cardinal.core.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.core.console.Logger;

import java.time.Duration;

public class DockerAccess {

    private static DockerAccess instance;
    private static DockerHttpClient dockerClientHttp;
    private static DockerClient dockerClient;

    public DockerAccess() {
        instance = this;
    }

    public static void init() {
        Logger logger = Cardinal.getInstance().getLogger();
        logger.info("Try to connect local docker server..");
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        dockerClientHttp = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();
        dockerClient = DockerClientImpl.getInstance(config, dockerClientHttp);
        DockerHttpClient.Request request = DockerHttpClient.Request.builder()
                .method(DockerHttpClient.Request.Method.GET)
                .path("/_ping")
                .build();
        try (DockerHttpClient.Response response = dockerClientHttp.execute(request)) {
            if (response.getStatusCode() == 200) {
                logger.info("Now connected to docker");
            } else {
                logger.error("Docker not found...");
                Cardinal.getInstance().stopServer();
            }
        }
    }

    public DockerHttpClient getDockerHttpClient() {
        return dockerClientHttp;
    }

    public DockerClient getDockerClient() {
        return dockerClient;
    }

    public static DockerAccess get() {
        return instance;
    }

}
