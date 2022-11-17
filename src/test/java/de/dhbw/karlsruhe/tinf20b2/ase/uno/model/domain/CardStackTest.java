package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardStackTest {

    @Test
    public void add() {
        List<Card> cards = new ArrayList<>();

        CardStack stack = new CardStack(cards);

        assertEquals(stack.getCardList().size(), 0);

        Card card = new Card(CardColor.BLUE);
        stack.add(card);

        assertEquals(stack.getCardList().get(0), card);
    }

    @Test
    public void remove() {
        List<Card> cards = new ArrayList<>();
        Card card = new Card(CardColor.BLUE);
        cards.add(card);

        CardStack stack = new CardStack(cards);

        assertEquals(stack.getCardList().size(), 1);

        stack.remove(card);

        assertEquals(stack.getCardList().size(), 0);
    }

    @Test
    public void isFinished() {
        List<Card> cards = new ArrayList<>();

        CardStack stack = new CardStack(cards);

        assertEquals(stack.isFinished(), true);
    }

    @Test
    public void isNotFinished() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(CardColor.BLUE));

        CardStack stack = new CardStack(cards);

        assertEquals(stack.isFinished(), false);
    }

}
