package fr.arcanmc.cardinal.api.template;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnvironmentVariable {

    private Map<String, String> environmentVariables;

    public String get(String key) {
        return this.environmentVariables.get(key);
    }

    public void set(String key, String value) {
        this.environmentVariables.put(key, value);
    }

    public void remove(String key) {
        this.environmentVariables.remove(key);
    }

    public boolean contains(String key) {
        return this.environmentVariables.containsKey(key);
    }

    public boolean isEmpty() {
        return this.environmentVariables.isEmpty();
    }

    public void clear() {
        this.environmentVariables.clear();
    }

    public int size() {
        return this.environmentVariables.size();
    }

}
