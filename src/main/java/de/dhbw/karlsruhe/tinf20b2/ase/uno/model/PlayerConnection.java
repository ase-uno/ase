package de.dhbw.karlsruhe.tinf20b2.ase.uno.model;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.Card;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.CardColor;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.CardStack;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.dto.PlayerDTO;

public interface PlayerConnection {

    Card input(Card active, CardStack cardStack);
    CardColor inputColor();

    void broadcastWinner(PlayerDTO winner);

    void broadcastActivePlayer(PlayerDTO player);

}
