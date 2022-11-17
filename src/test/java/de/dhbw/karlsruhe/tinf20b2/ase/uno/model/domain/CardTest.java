package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    public void compatibleSameColor() {
        Card card = new Card(CardColor.BLUE, new CardNumber(1));

        Card card2 = new Card(CardColor.BLUE, new CardNumber(2));

        assertEquals(card.isCompatibleWith(card2), true);
    }

    @Test
    public void compatibleSameNumber() {
        Card card = new Card(CardColor.BLUE, new CardNumber(1));

        Card card2 = new Card(CardColor.RED, new CardNumber(1));

        assertEquals(card.isCompatibleWith(card2), true);
    }

    @Test
    public void compatibleSameAction() {
        Card card = new Card(CardColor.BLUE, new CardAction(Action.BLOCK, 0));

        Card card2 = new Card(CardColor.RED, new CardAction(Action.BLOCK, 0));

        assertEquals(card.isCompatibleWith(card2), true);
    }

    @Test
    public void compatibleChangeColor() {
        Card card = new Card(CardColor.BLUE);

        Card card2 = new Card(CardColor.RED, new CardAction(Action.CHANGE_COLOR, 0));

        assertEquals(card.isCompatibleWith(card2), true);
    }

    @Test
    public void compatibleIncompatible() {
        Card card = new Card(CardColor.BLUE, new CardNumber((1)));

        Card card2 = new Card(CardColor.RED, new CardNumber(2));

        assertEquals(card.isCompatibleWith(card2), false);
    }

}
