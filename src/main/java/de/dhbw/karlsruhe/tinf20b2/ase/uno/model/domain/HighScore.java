package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain;

import java.util.HashMap;
import java.util.Map;

public class HighScore {

    private final Map<SimplePlayer, Integer> elements;

    public HighScore() {
        this.elements = new HashMap<>();
    }

    public HighScore(Map<SimplePlayer, Integer> elements) {
        this.elements = elements;
    }

    public Map<SimplePlayer, Integer> getElements() {
        return elements;
    }
}
