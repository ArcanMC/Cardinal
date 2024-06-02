package fr.arcanmc.cardinal.server.player;

import fr.arcanmc.cardinal.api.game.GameInstance;
import fr.arcanmc.cardinal.api.player.CardinalPlayer;
import fr.arcanmc.cardinal.server.ServerService;
import fr.arcanmc.cardinal.server.game.GameManager;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class PlayerManager {

    private final Set<CardinalPlayer> players;

    public PlayerManager() {
        this.players = new HashSet<>();
    }

    public void addPlayer(CardinalPlayer player) {
        GameManager gameManager = ServerService.get().getGameManager();
        GameInstance gameInstance = gameManager.getGameInstance(player.getCurrentServer());
        if (gameInstance != null) {
            gameInstance.addPlayer(player);
            gameManager.updateGameInstance(gameInstance);
        }
        players.add(player);
    }

    public void removePlayer(CardinalPlayer player) {
        removeFromGame(player);
        players.remove(player);
    }

    public void removePlayer(UUID uuid) {
        removeFromGame(getPlayer(uuid));
        players.removeIf(player -> player.getUuid().equals(uuid));
    }

    public CardinalPlayer getPlayer(UUID uuid) {
        return players.stream().filter(player -> player.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    public CardinalPlayer getPlayer(String name) {
        return players.stream().filter(player -> player.getName().equals(name)).findFirst().orElse(null);
    }

    public CardinalPlayer getPlayer(int bungeeId) {
        return players.stream().filter(player -> player.getBungeeId() == bungeeId).findFirst().orElse(null);
    }

    public CardinalPlayer getPlayer(String name, int bungeeId) {
        return players.stream().filter(player -> player.getName().equals(name) && player.getBungeeId() == bungeeId).findFirst().orElse(null);
    }

    private void removeFromGame(CardinalPlayer player) {
        GameManager gameManager = ServerService.get().getGameManager();
        GameInstance gameInstance = gameManager.getGameInstance(player.getCurrentServer());
        if (gameInstance != null) {
            gameInstance.removePlayer(player);
            gameManager.updateGameInstance(gameInstance);
        }
    }

}
