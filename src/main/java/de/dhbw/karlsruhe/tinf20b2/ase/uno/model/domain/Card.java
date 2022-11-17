package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain;

import java.util.Objects;

public class Card {

    private CardColor cardColor;
    private CardNumber cardNumber;
    private CardAction action;

    public Card(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public Card(CardColor cardColor, CardNumber cardNumber) {
        this.cardColor = cardColor;
        this.cardNumber = cardNumber;
    }

    public Card(CardColor cardColor, CardAction action) {
        this.cardColor = cardColor;
        this.action = action;
    }

    public Card(CardAction action) {
        this.action = action;
    }

    /**
     * Should only be used by mapper
     */
    public Card(CardColor cardColor, CardNumber cardNumber, CardAction action) {
        this.cardColor = cardColor;
        this.cardNumber = cardNumber;
        this.action = action;
    }

    public CardColor getColor() {
        return cardColor;
    }

    public CardNumber getCardNumber() {
        return cardNumber;
    }

    public boolean hasAction() {
        return action != null;
    }

    public CardAction getAction() {
        return action;
    }

    public boolean isCompatibleWith(Card card) {
        boolean colorsEqual = cardColor != null && getColor() == card.getColor();
        boolean numbersEqual = cardNumber != null && getCardNumber().equals(card.getCardNumber());
        boolean hasAction = action != null && action.getAction() == Action.CHANGE_COLOR
            || card.action != null && card.action.getAction() == Action.CHANGE_COLOR;
        boolean actionsEqual = action != null && getAction().equals(card.getAction());

        return colorsEqual || numbersEqual || hasAction || actionsEqual;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardColor=" + cardColor +
                ", cardNumber=" + cardNumber +
                ", action=" + action +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cardColor == card.cardColor && Objects.equals(cardNumber, card.cardNumber) && Objects.equals(action, card.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardColor, cardNumber, action);
    }
}
