package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.plugins;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.console.ConsoleColor;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.console.ConsoleOut;

public class ConsoleAdapter implements ConsoleOut {

    @Override
    public void print(ConsoleColor color, String message) {
        System.out.print(colorChange(color.getColorNumber()) + message + colorChange(ConsoleColor.RESET.getColorNumber()));
    }

    @Override
    public void error(String message) {
        System.err.println(colorChange(ConsoleColor.RED.getColorNumber()) + message);
    }

    private String colorChange(int color) {
        return (char)27 + "[" + color + "m";
    }
}
