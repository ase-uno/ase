package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain;

import java.awt.*;

public enum CardColor {

    RED("Red", Color.RED),
    YELLOW("Yellow", Color.YELLOW),
    BLUE("Blue", Color.BLUE),
    GREEN("Green", Color.GREEN);

    private final Color color;
    private final String name;

    CardColor(String name, Color color) {
        this.color = color;
        this.name = name;
    }

    public Color getColor() {
        return this.color;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CardColor{" +
                "name=" + name +
                ", color=" + color +
                '}';
    }
}
