package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.console;

public interface ConsoleOut {

    default void print(String message) {
        print(ConsoleColor.RESET, message);
    }

    default void println(String message) {
        println(ConsoleColor.RESET, message);
    }

    default void println(ConsoleColor color, String message) {
        print(color, message + "\n");
    }

    default void println() {
        println("");
    }

    void print(ConsoleColor color, String message);

    void error(String message);

}
