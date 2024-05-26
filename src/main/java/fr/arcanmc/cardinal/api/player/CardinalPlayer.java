package fr.arcanmc.cardinal.api.player;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
public class CardinalPlayer {

    @Getter
    private UUID uuid;
    @Getter
    private String name;
    @Getter
    private String currentServer;
    private Map<String, String> data;

    public CardinalPlayer(UUID uuid, String name, String currentServer, Map<String, String> data) {
        this.uuid = uuid;
        this.name = name;
        this.currentServer = currentServer;
        this.data = data;
    }

    public Map<String, String> getAllData() {
        return data;
    }

    public String getData(String key) {
        return data.get(key);
    }

    public void setData(String key, String value) {
        data.put(key, value);
    }

    public void removeData(String key) {
        data.remove(key);
    }

    public boolean hasData(String key) {
        return data.containsKey(key);
    }

    public boolean hasDataValue(String value) {
        return data.containsValue(value);
    }

    public boolean hasDataEntry(String key, String value) {
        return data.containsKey(key) && data.containsValue(value);
    }

    public void clearData() {
        data.clear();
    }

}
