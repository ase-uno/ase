package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class CardNumberTest {

    @Test
    public void setNumberSuccess() {
        CardNumber number = new CardNumber(1);

        number.setNumber(2);

        assertEquals(number.getNumber(), 2);
    }

    @Test
    public void setNumberFailNeg() {
        CardNumber number = new CardNumber(1);

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                number.setNumber(-1);
            }
        });
    }

    @Test
    public void setNumberFailPos() {
        CardNumber number = new CardNumber(1);

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                number.setNumber(10);
            }
        });
    }

}
