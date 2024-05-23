package fr.arcanmc.cardinal.utils.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

public class Json {

    private static ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    public static JsonNode parse(String jsonSrc) throws JsonProcessingException {
        return defaultObjectMapper().readTree(jsonSrc);
    }

    public static <A> A fromJson(String jsonSrc, Class<A> clazz) throws JsonProcessingException {
        return defaultObjectMapper().readValue(jsonSrc, clazz);
    }

    public static String stringify(Object obj) throws JsonProcessingException {
        return generateJson(obj, false);
    }

    public static String prettyStringify(Object obj) throws JsonProcessingException {
        return generateJson(obj, true);
    }

    public static JsonNode toJson(Object object) throws JsonProcessingException {
        return defaultObjectMapper().valueToTree(object);
    }

    private static String generateJson(Object object, boolean pretty) throws JsonProcessingException {
        ObjectWriter writer = defaultObjectMapper().writer();
        if (pretty) writer = writer.with(SerializationFeature.INDENT_OUTPUT);
        return writer.writeValueAsString(object);
    }


}
