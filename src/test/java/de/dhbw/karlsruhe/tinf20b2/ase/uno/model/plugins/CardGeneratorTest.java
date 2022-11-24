package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.plugins;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.Card;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardGeneratorTest {

    @Test
    public void listAllCards() {
        CardGenerator generator = new CardGenerator();

        List<Card> cards = generator.listAllCards();

        assertEquals(cards.size(), 54);
    }

}
