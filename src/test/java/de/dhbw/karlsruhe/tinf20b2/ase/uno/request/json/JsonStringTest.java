package de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonStringTest {

    @Test
    public void getValue() {
        String s = "Wird hier gebaut?";

        JsonString string = new JsonString(s);

        assertEquals(string.getValue(), s);
    }

    @Test
    public void toJson() {
        JsonString string = new JsonString("I bims 1 String");

        assertEquals(string.toJson(), "\"I bims 1 String\"");
    }

    @Test
    public void toJsonEscape1() {
        JsonString string = new JsonString("\\");

        assertEquals(string.toJson(), "\"\\\\\"");
    }

    @Test
    public void toJsonEscape2() {
        JsonString string = new JsonString("\\\\");

        assertEquals(string.toJson(), "\"\\\\\\\\\"");
    }

}
