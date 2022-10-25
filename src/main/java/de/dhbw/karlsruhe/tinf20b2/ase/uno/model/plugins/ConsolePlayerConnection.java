package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.plugins;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.*;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.Card;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.CardColor;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.CardStack;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.dto.PlayerDTO;

import java.util.Scanner;

public class ConsolePlayerConnection implements PlayerConnection {
    @Override
    public Card input(Card active, CardStack cardStack) {

        System.out.println("Input card: ");
        System.out.println();
        System.out.println("Active: ");
        System.out.println(cardToString(active));
        System.out.println();

        for(int i = 0; i<cardStack.getCardList().size(); i++) {
            System.out.println(i + ") " + cardToString(cardStack.getCardList().get(i)));
        }

        Scanner scanner = new Scanner(System.in);
        int input = -2;
        do {
            try {
                System.out.println("Input:");
                input = scanner.nextInt();
            } catch (Exception ignored) {
                //if an error occurs, new user-input is requested by the loop
            }
        } while(input < -1 || input >= cardStack.getCardList().size());
        System.out.println();
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
        System.out.println("Input card");

        for(int i = 0; i < CardColor.values().length; i++) {
            System.out.println(i + ") " + CardColor.values()[i].getName());
        }

        Scanner scanner = new Scanner(System.in);
        int input = -1;
        do {
            try {
                System.out.println("Input:");
                input = scanner.nextInt();
            } catch (Exception ignored) {
                //if an error occurs, new user-input is requested by the loop
            }
        } while(input < 0 || input >= CardColor.values().length);
        System.out.println();

        return CardColor.values()[input];
    }

    @Override
    public void broadcastWinner(PlayerDTO winner) {
        if(winner == null) {
            System.out.println("Tie");
        } else {
            System.out.println("Winner: " + winner.getName());
        }
    }

    @Override
    public void broadcastActivePlayer(PlayerDTO player) {
        System.out.println("Now playing: " + player.getName());
    }
}
