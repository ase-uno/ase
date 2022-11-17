package de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json;

import java.util.List;
import java.util.stream.Collectors;

public class JsonArray implements JsonElement {

    private final List<JsonElement> elements;

    public JsonArray(List<JsonElement> elements) {
        this.elements = elements;
    }

    public List<JsonElement> getElements() {
        return elements;
    }

    @Override
    public String toJson() {
        return "[" + elements.stream()
                .map(JsonElement::toJson)
                .collect(Collectors.joining(","))
                + "]";
    }
}
