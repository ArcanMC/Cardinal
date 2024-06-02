package fr.arcanmc.cardinal.server.bungeecord;

import fr.arcanmc.cardinal.api.bungee.Bungee;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class BungeeManager {

    private final Set<Bungee> bungees;

    public BungeeManager() {
        this.bungees = new HashSet<>();
    }

    public void addBungee(Bungee bungee) {
        this.bungees.add(bungee);
    }

    public void removeBungee(int id) {
        this.bungees.removeIf(bungee -> bungee.getId() == id);
    }

    public void removeBungee(Bungee bungee) {
        this.bungees.remove(bungee);
    }

    public Bungee getBungee(int id) {
        return this.bungees.stream().filter(bungee -> bungee.getId() == id).findFirst().orElse(null);
    }

    public void clearBungees() {
        this.bungees.clear();
    }

    public boolean isBungeeConnected(int id) {
        return this.bungees.stream().anyMatch(bungee -> bungee.getId() == id);
    }

    public boolean isBungeeConnected(Bungee bungee) {
        return this.bungees.contains(bungee);
    }

    public int getBungeeCount() {
        return this.bungees.size();
    }

}
