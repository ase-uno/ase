package de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json;

public class JsonString extends JsonElement {

    private final String value;

    public JsonString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value.replace("\\\"", "\"").replace("\\\\", "\\");
    }

    @Override
    public String toJson() {
        return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }

}
