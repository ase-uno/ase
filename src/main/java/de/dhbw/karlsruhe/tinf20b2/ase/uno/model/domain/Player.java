package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain;

public class Player extends SimplePlayer {

    private final CardStack cardStack;


    public Player(String name, CardStack cardStack) {
        super(name);
        this.cardStack = cardStack;
    }


    public CardStack getCardStack() {
        return cardStack;
    }
}
