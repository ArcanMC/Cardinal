package fr.arcanmc.cardinal.module;

import lombok.Getter;

@Getter
public class ModuleInfo {

    private String name;
    private String description;
    private String author;
    private String version;
    private String main;

    public ModuleInfo(FileConfiguration file) {
        name = file.get("name", String.class);
        description = file.get("description", String.class) == null ? "" : file.get("description", String.class);
        author = file.get("author", String.class);
        version = file.get("version", String.class);
        main = file.get("main", String.class);
    }

}
