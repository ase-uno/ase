package de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonNumberTest {

    @Test
    public void getValue() {
        int value = 1;

        JsonNumber number = new JsonNumber(value);

        assertEquals(number.getValue(), value);
    }

    @Test
    public void toJson() {
        int value = 1;

        JsonNumber number = new JsonNumber(value);

        assertEquals(number.toJson(), value + "");
    }

}
