package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain;

import java.util.Objects;

public class SimplePlayer {

    private final String name;

    public SimplePlayer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof SimplePlayer that)) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
