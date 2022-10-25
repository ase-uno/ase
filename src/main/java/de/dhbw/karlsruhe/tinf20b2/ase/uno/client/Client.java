package de.dhbw.karlsruhe.tinf20b2.ase.uno.client;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.client.plugins.SocketConnection;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.ConnectionInstance;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.console.ConsoleOut;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.plugins.ConsolePlayerConnection;

import java.io.IOException;
import java.util.Scanner;

public class Client extends ConnectionInstance {

    private final ConsoleOut console;

    public Client(String name, ConsoleOut console) {
        super(name);
        this.console = console;

        try {
            connect();
        } catch (Exception exception) {
            console.error("");
        }
    }

    private void connect() throws IOException {
        console.println("Ziel-IP:");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        new SocketConnection(input, getLocalName(), new ConsolePlayerConnection(console), console);
    }
}
