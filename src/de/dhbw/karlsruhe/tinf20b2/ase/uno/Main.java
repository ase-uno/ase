package de.dhbw.karlsruhe.tinf20b2.ase.uno;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.client.Client;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.server.Server;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Willkommen");
        System.out.println();

        String name = inputName();
        int mode = inputMode();
        start(name, mode);
    }

    private static String inputName() {
        System.out.println("Wie heißt du?");

        Scanner scanner = new Scanner(System.in);

        String name;
        do {
            name = scanner.nextLine();
        } while (name.strip().length() == 0);

        System.out.println();

        return name;
    }



    private static int inputMode() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("0) Server öffnen");
        System.out.println("1) Mit anderem Server verbinden");
        System.out.println();
        System.out.println("Input:");

        int input = -1;
        do {
            try {
                input = scanner.nextInt();
            } catch (Exception ignored) {
                //if an error occurs, new user-input is requested by the loop
            }
        } while (input < 0 || input > 1);

        return input;
    }

    private static void start(String name, int mode) throws IOException {

        if(mode == 0) {
            new Server(name);
        } else {
            new Client(name);
        }
    }
}