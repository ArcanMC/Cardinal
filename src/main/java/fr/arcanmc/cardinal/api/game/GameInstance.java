package fr.arcanmc.cardinal.api.game;

import fr.arcanmc.cardinal.api.player.CardinalPlayer;
import fr.arcanmc.cardinal.client.ClientService;
import fr.arcanmc.cardinal.client.template.TemplateManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GameInstance {

    private Date createdAt = new Date();
    private int clientId;
    private String templateName;
    private String id;
    private String name;
    private String ip;
    private int port;
    @Setter
    private ServerStatus status;
    @Setter
    private boolean hasHost;
    @Setter
    private UUID host;
    private List<CardinalPlayer> players;

    public GameInstance(int clientId, String templateName, String id, String ip, int port, UUID host) {
        this.clientId = clientId;
        this.templateName = templateName;
        this.id = id;
        this.ip = ip;
        this.port = port;
        this.host = host;
        this.status = ServerStatus.GENERATING;

        this.generateName();

        if (host == null) {
            this.hasHost = false;
        }
        players = new ArrayList<>();
    }

    public void generateName() {
        this.name = ClientService.get().getTemplateManager().getTemplate(templateName).getPrefix() + "-" + this.id;
    }

    public void addPlayer(CardinalPlayer player) {
        players.add(player);
    }

    public void removePlayer(CardinalPlayer player) {
        players.remove(player);
    }

    public boolean hasPlayer(CardinalPlayer player) {
        return players.contains(player);
    }

    public boolean hasPlayer(UUID uuid) {
        return players.stream().anyMatch(player -> player.getUuid().equals(uuid));
    }

    public CardinalPlayer getPlayer(UUID uuid) {
        return players.stream().filter(player -> player.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    public CardinalPlayer getPlayer(String name) {
        return players.stream().filter(player -> player.getName().equals(name)).findFirst().orElse(null);
    }

    public void clearPlayers() {
        players.clear();
    }
}
