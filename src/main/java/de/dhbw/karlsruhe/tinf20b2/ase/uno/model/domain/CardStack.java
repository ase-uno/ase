package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain;

import java.util.ArrayList;
import java.util.List;

public class CardStack {

    private final List<Card> cardList;

    public CardStack(List<Card> cardList) {
        this.cardList = new ArrayList<>(cardList);
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void remove(Card card) {
        if(card == null) return;
        this.cardList.remove(card);
    }

    public Card consumeFirst() {
        Card card = cardList.get(0);
        cardList.remove(card);
        return card;
    }

    public void add(Card card) {
        this.cardList.add(card);
    }

    public boolean isFinished() {
        return this.cardList.isEmpty();
    }
}
