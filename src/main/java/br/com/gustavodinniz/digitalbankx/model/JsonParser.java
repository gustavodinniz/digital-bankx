package br.com.gustavodinniz.digitalbankx.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class JsonParser {

    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @SneakyThrows
    public static String objectToStringJson(Object value) {
        return mapper.writeValueAsString(value);
    }

    @SneakyThrows
    public static <T> T stringJsonToObject(String json, Class<T> clazz) {
        return mapper.readValue(json, clazz);
    }

    @SneakyThrows
    public static <T> List<T> stringJsonToList(String json, Class<T> clazz) {
        return mapper.readValue(json, new TypeReference<List<T>>() {
            @Override
            public Type getType() {
                return mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            }
        });
    }

    @SneakyThrows
    public static Map<String, Object> objectToMap(Object object) {
        return mapper.convertValue(object, new TypeReference<Map<String, Object>>() {
        });
    }

}
