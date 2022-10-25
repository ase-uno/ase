package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.dto;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.PlayerConnection;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.Card;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.Player;

public class PlayerWithConnection {

    private final Player player;
    private final PlayerConnection playerConnection;

    public PlayerWithConnection(Player player, PlayerConnection playerConnection) {
        this.player = player;
        this.playerConnection = playerConnection;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerConnection getPlayerConnection() {
        return playerConnection;
    }

    public Card input(Card activeCard) {
        return this.playerConnection.input(activeCard, this.player.getCardStack());
    }
}
