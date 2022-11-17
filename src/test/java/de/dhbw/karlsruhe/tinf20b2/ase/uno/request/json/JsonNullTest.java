package de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonNullTest {

    @Test
    public void toJson() {
        JsonNull jsonNull = new JsonNull();

        assertEquals(jsonNull.toJson(), "null");
    }

}
