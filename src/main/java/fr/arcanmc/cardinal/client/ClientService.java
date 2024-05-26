package fr.arcanmc.cardinal.client;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.event.events.client.ClientStarted;
import fr.arcanmc.cardinal.api.event.events.client.ClientStopped;
import fr.arcanmc.cardinal.api.service.Service;
import fr.arcanmc.cardinal.client.event.ClientStartedEvent;
import fr.arcanmc.cardinal.client.event.ClientStoppedEvent;
import fr.arcanmc.cardinal.client.game.GameManager;
import fr.arcanmc.cardinal.client.template.TemplateManager;
import fr.arcanmc.cardinal.file.FileConfiguration;
import lombok.Getter;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;

@Getter
public class ClientService extends Service {

    private static ClientService instance;

    private TemplateManager templateManager;
    private GameManager gameManager;

    private String myIp = "notFound";
    private int myId = -1;
    private String myName = "anonymous";

    @Override
    public String getName() {
        return "Client";
    }

    @Override
    public void onEnable() {
        instance = this;

        this.myIp = getIp();

        File configFile = new File("client.yml");
        if (!configFile.exists()) {
            try (InputStream in = getClass().getClassLoader().getResourceAsStream("client/client.yml")) {
                Files.copy(in, configFile.toPath());
            } catch (IOException e) {
                Cardinal.getInstance().getLogger().error("Could not create client.yml file");
            }
        }

        try {
            FileConfiguration config = new FileConfiguration(configFile);
            this.myId = config.get("id", Integer.class);
            this.myName = config.get("name", String.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        this.templateManager = new TemplateManager();
        this.gameManager = new GameManager();

        new ClientStartedEvent(new ClientStarted(this.myId, this.myName, this.myIp)).publish();
    }

    @Override
    public void onDisable() {
        this.templateManager.save();
        new ClientStoppedEvent(new ClientStopped(this.myIp)).publish();
    }

    private String getIp() {
        String ip = "notFound";
        try {
            URL url = new URL("http://checkip.amazonaws.com/");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            ip = br.readLine();
        } catch (Exception e) {
            Cardinal.getInstance().getLogger().error("Could not find current IP address");
        }
        return ip;
    }

    public static ClientService get() {
        return instance;
    }
}
