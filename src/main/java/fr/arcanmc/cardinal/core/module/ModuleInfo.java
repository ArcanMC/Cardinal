package fr.arcanmc.cardinal.core.module;

import fr.arcanmc.cardinal.file.FileConfiguration;
import lombok.Getter;

@Getter
public class ModuleInfo {

    private final String name;
    private final String description;
    private final String author;
    private final String version;
    private final String main;

    public ModuleInfo(FileConfiguration file) {
        name = file.get("name", String.class);
        description = file.get("description", String.class) == null ? "" : file.get("description", String.class);
        author = file.get("author", String.class);
        version = file.get("version", String.class);
        main = file.get("main", String.class);
    }

}
