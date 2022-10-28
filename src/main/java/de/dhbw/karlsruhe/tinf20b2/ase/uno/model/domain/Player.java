package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain;

import java.util.Objects;

public class Player extends SimplePlayer {

    private final CardStack cardStack;


    public Player(String name, CardStack cardStack) {
        super(name);
        this.cardStack = cardStack;
    }


    public CardStack getCardStack() {
        return cardStack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimplePlayer)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cardStack);
    }
}
