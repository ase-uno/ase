package de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json;

public class JsonNull extends JsonElement {

    @Override
    public String toJson() {
        return "null";
    }
}
