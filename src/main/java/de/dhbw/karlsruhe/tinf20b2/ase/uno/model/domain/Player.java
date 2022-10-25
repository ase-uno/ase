package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain;

public class Player {

    private final String name;
    private final CardStack cardStack;


    public Player(String name, CardStack cardStack) {
        this.name = name;
        this.cardStack = cardStack;
    }

    public String getName() {
        return this.name;
    }


    public CardStack getCardStack() {
        return cardStack;
    }
}
