package de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json;

public class JsonNumber implements JsonElement {

    private final Number value;

    public JsonNumber(Number number) {
        this.value = number;
    }

    public JsonNumber(int number) {
        this.value = number;
    }

    public Number getValue() {
        return value;
    }

    @Override
    public String toJson() {
        return value + "";
    }
}
