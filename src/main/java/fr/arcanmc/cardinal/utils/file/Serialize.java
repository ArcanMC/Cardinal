package fr.arcanmc.cardinal.utils.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class Serialize {
    private final Gson gson = (new GsonBuilder())
            .setPrettyPrinting()
            .serializeNulls()
            .disableHtmlEscaping()
            .create();

    public String serialize(Object object) {
        return this.gson.toJson(object);
    }

    public <T> Object deserialize(String json, Class<T> clazz) {
        return this.gson.fromJson(json, clazz);
    }

    public <T> Object deserialize(String json, Type type) {
        return this.gson.fromJson(json, type);
    }
}
