package de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonObject extends JsonElement {


    private final Map<String, JsonElement> elements;

    public JsonObject() {
        this.elements = new HashMap<>();
    }

    public JsonObject(Map<String, JsonElement> elements) {
        this.elements = elements;
    }

    public Map<String, JsonElement> getElements() {
        return elements;
    }

    public JsonElement get(String key) {
        return elements.getOrDefault(key, null);
    }

    public void set(String key, JsonElement jsonElement) {
        elements.put(key, jsonElement);
    }
    @Override
    public String toJson() {
        return "{" +
                elements.entrySet()
                        .stream()
                        .map(e -> new JsonString(e.getKey()).toJson() + ":" + e.getValue().toJson())
                        .collect(Collectors.joining(","))
                + "}";
    }
}
