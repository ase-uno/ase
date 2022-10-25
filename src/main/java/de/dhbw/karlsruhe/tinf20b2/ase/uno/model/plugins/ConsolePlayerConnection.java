package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.plugins;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.*;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.console.ConsoleOut;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.Card;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.CardColor;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.CardStack;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.dto.PlayerDTO;

import java.util.Scanner;

public class ConsolePlayerConnection implements PlayerConnection {

    private final ConsoleOut console;

    public ConsolePlayerConnection(ConsoleOut console) {
        this.console = console;
    }

    @Override
    public Card input(Card active, CardStack cardStack) {

        console.println("Input card: ");
        console.println();
        console.println("Active: ");
        console.println(cardToString(active));
        console.println();

        for(int i = 0; i<cardStack.getCardList().size(); i++) {
            console.println(i + ") " + cardToString(cardStack.getCardList().get(i)));
        }

        Scanner scanner = new Scanner(System.in);
        int input = -2;
        do {
            try {
                console.println("Input:");
                input = scanner.nextInt();
            } catch (Exception ignored) {
                //if an error occurs, new user-input is requested by the loop
            }
        } while(input < -1 || input >= cardStack.getCardList().size());
        console.println();
        if(input == -1) return null;

        return cardStack.getCardList().get(input);
    }

    private String cardToString(Card card) {
        Integer number = null;
        String color = null;
        if(card.getCardNumber() != null) number = card.getCardNumber().getNumber();
        if(card.getColor() != null) color = card.getColor().getName();
        return "color=" + color + ", cardNumber=" + number + ", action=" + card.getAction();
    }

    @Override
    public CardColor inputColor() {
        console.println("Input card");

        for(int i = 0; i < CardColor.values().length; i++) {
            console.println(i + ") " + CardColor.values()[i].getName());
        }

        Scanner scanner = new Scanner(System.in);
        int input = -1;
        do {
            try {
                console.println("Input:");
                input = scanner.nextInt();
            } catch (Exception ignored) {
                //if an error occurs, new user-input is requested by the loop
            }
        } while(input < 0 || input >= CardColor.values().length);
        console.println();

        return CardColor.values()[input];
    }

    @Override
    public void broadcastWinner(PlayerDTO winner) {
        if(winner == null) {
            console.println("Tie");
        } else {
            console.println("Winner: " + winner.getName());
        }
    }

    @Override
    public void broadcastActivePlayer(PlayerDTO player) {
        console.println("Now playing: " + player.getName());
    }
}
