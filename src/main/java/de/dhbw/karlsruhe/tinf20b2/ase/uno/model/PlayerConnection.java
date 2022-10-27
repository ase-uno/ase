package de.dhbw.karlsruhe.tinf20b2.ase.uno.model;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.*;

public interface PlayerConnection {

    Card input(Card active, CardStack cardStack);
    CardColor inputColor();

    void broadcastWinner(SimplePlayer winner);

    void broadcastActivePlayer(SimplePlayer player);

    void broadcastHighScore(HighScore highScore);

}
