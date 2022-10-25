package de.dhbw.karlsruhe.tinf20b2.ase.uno.client;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.client.plugins.SocketConnection;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.plugins.ConsolePlayerConnection;

import java.io.IOException;
import java.util.Scanner;

public class Client {

    public Client(String name) throws IOException {
        connect(name);
    }

    private void connect(String name) throws IOException {
        System.out.println("Ziel-IP:");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        new SocketConnection(input, name, new ConsolePlayerConnection());
    }
}
