package de.dhbw.karlsruhe.tinf20b2.ase.uno.model;

public class ConnectionInstance {

    private final String name;

    public ConnectionInstance(String name) {
        this.name = name;
    }

    public String getLocalName() {
        return name;
    }
}
