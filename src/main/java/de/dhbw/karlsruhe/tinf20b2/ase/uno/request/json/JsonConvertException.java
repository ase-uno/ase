package de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json;

public class JsonConvertException extends Exception {

    public JsonConvertException(String message) {
        super(message);
    }

    public JsonConvertException(int index) {
        this("Malformed json at index " + index);
    }
}
