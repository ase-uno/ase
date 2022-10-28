package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighScore {

    private Map<SimplePlayer, Integer> elements;

    public HighScore() {
        this.elements = new HashMap<>();
    }

    public HighScore(Map<SimplePlayer, Integer> elements) {
        this.elements = elements;
    }

    public Map<SimplePlayer, Integer> getElements() {
        return elements;
    }

    public HighScore filter(List<SimplePlayer> onlyInclude) {
        Map<SimplePlayer, Integer> newMap = new HashMap<>();
        elements.forEach((k, v) -> {
            if(onlyInclude.contains(k)) newMap.put(k, v);
        });

        this.elements = newMap;
        return this;
    }
}
