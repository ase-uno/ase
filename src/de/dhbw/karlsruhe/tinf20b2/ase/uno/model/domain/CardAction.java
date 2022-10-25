package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain;

import java.util.Objects;

public class CardAction {

    private final Action action;
    private final int draw;

    public CardAction(Action action, int draw) {
        this.action = action;
        this.draw = draw;
    }

    public Action getAction() {
        return action;
    }

    public int getDraw() {
        return draw;
    }

    @Override
    public String toString() {
        return "CardAction{" +
                "action=" + action +
                ", draw=" + draw +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardAction that = (CardAction) o;
        return draw == that.draw && action == that.action;
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, draw);
    }
}
