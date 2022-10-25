package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain;

import java.util.Objects;

public class CardNumber {
    private Integer number;

    public CardNumber(Integer number) {
        setNumber(number);
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        if(number < 0 || number > 9) {
            throw new IllegalArgumentException("Numbers only allowed in Interval [0, 9]");
        }
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardNumber that = (CardNumber) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "CardNumber{" +
                "number=" + number +
                '}';
    }
}
