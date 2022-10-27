package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.plugins;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.*;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.*;

import java.util.Random;

public class NPCPlayerConnection implements PlayerConnection {


    private final String name;

    public NPCPlayerConnection(String name) {
        this.name = name;
    }

    private final Random random = new Random();

    @Override
    public Card input(Card active, CardStack cardStack) {
        System.out.println(name + ": cards: " + cardStack.getCardList().size());
        for(Card card: cardStack.getCardList()) {
            if(active.isCompatibleWith(card)) {
                System.out.println(name + ": set" + card);
                return card;
            }
        }
        System.out.println(name + ": draw");
        return null;
    }

    @Override
    public CardColor inputColor() {
        return CardColor.values()[random.nextInt(CardColor.values().length)];
    }

    @Override
    public void broadcastWinner(SimplePlayer winner) {
        //only for testing, so no broadcast ist needed
    }

    @Override
    public void broadcastActivePlayer(SimplePlayer player) {
        //only for testing, so no broadcast ist needed
    }

    @Override
    public void broadcastHighScore(HighScore highScore) {
        //only for testing, so no broadcast ist needed
    }
}
